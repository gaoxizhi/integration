package net.gaox.jpa.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import net.gaox.jpa.config.annotation.Comment;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * <p> 处理 字段注释 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-27 02:50
 */
@Slf4j
@Component
public class CommentIntegrator implements Integrator {
    public static final CommentIntegrator INSTANCE = new CommentIntegrator();

    public CommentIntegrator() {
        super();
    }

    /**
     * Perform comment integration.
     *
     * @param metadata        The "compiled" representation of the mapping information
     * @param sessionFactory  The session factory being created
     * @param serviceRegistry The session factory's service registry
     */
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory,
                          SessionFactoryServiceRegistry serviceRegistry) {
        processComment(metadata);
    }

    /**
     * Not used.
     *
     * @param sessionFactoryImplementor     The session factory being closed.
     * @param sessionFactoryServiceRegistry That session factory's service registry
     */
    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor,
                             SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
    }

    /**
     * 注释处理
     *
     * @param metadata process annotation of this {@code Metadata}.
     */
    private void processComment(Metadata metadata) {
        for (PersistentClass persistentClass : metadata.getEntityBindings()) {
            // 表注解：从 Comment 或者 ApiModel.description 获取
            Class<?> clz = persistentClass.getMappedClass();
            if (clz.isAnnotationPresent(Comment.class) || clz.isAnnotationPresent(ApiModel.class)) {
                String comment = clz.getAnnotation(Comment.class).value();
                comment = StringUtils.isNotBlank(comment) ? comment : clz.getAnnotation(ApiModel.class).description();
                persistentClass.getTable().setComment(comment);
            }

            // Process Comment annotations of identifier.
            Property identifierProperty = persistentClass.getIdentifierProperty();
            if (identifierProperty != null) {
                fieldComment(persistentClass, identifierProperty.getName());
            } else {
                org.hibernate.mapping.Component component = persistentClass.getIdentifierMapper();
                if (component != null) {
                    // noinspection unchecked
                    Iterator<Property> iterator = component.getPropertyIterator();
                    while (iterator.hasNext()) {
                        fieldComment(persistentClass, iterator.next().getName());
                    }
                }
            }

            // 字段注解：从 Comment 或者 ApiModelProperty.value 获取
            // noinspection unchecked
            Iterator<Property> iterator = persistentClass.getPropertyIterator();
            while (iterator.hasNext()) {
                fieldComment(persistentClass, iterator.next().getName());
            }
        }
    }

    /**
     * 从字段注解获取 注释
     *
     * @param persistentClass class
     * @param fieldName       fieldName
     */
    private void fieldComment(PersistentClass persistentClass, String fieldName) {
        try {
            Field field = getField(persistentClass, fieldName);
            if (field.isAnnotationPresent(Comment.class) || field.isAnnotationPresent(ApiModelProperty.class)) {
                String comment = field.getAnnotation(Comment.class).value();
                comment = StringUtils.isNotBlank(comment) ? comment : field.getAnnotation(ApiModelProperty.class).value();
                Property property = persistentClass.getProperty(fieldName);
                String sqlColumnName = property.getValue().getColumnIterator().next().getText();
                Iterator<org.hibernate.mapping.Column> columnIterator = persistentClass.getTable().getColumnIterator();
                while (columnIterator.hasNext()) {
                    org.hibernate.mapping.Column column = columnIterator.next();
                    if (sqlColumnName.equalsIgnoreCase(column.getName())) {
                        column.setComment(comment);
                        break;
                    }
                }
            }
        } catch (NoSuchFieldException | SecurityException ignored) {
            String comment = persistentClass.getTable().getComment();
            comment = StringUtils.isNotBlank(comment) ? comment : persistentClass.getJpaEntityName();

            log.warn("处理[{}]表字段[{}]时出现异常: ", comment, fieldName, ignored);
        }
    }

    /**
     * 通过名称获取 Field
     *
     * @param persistentClass class
     * @param fieldName       fieldName
     * @return field
     * @throws NoSuchFieldException
     */
    private static Field getField(PersistentClass persistentClass, String fieldName) throws NoSuchFieldException {
        Class mappedClass = persistentClass.getMappedClass();

        // 尝试获取
        try {
            return mappedClass.getDeclaredField(fieldName);
        } catch (Exception e) {
        }

        // 尝试从父类获取
        Class superclass = mappedClass.getSuperclass();
        do {
            try {
                return superclass.getDeclaredField(fieldName);
            } catch (Exception e) {
                superclass = superclass.getSuperclass();
            }
        } while (null != superclass);

        throw new NoSuchFieldException(fieldName);
    }

}