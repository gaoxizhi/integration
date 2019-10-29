package net.gaox.ip.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/10/29 18:48
 */
public class XmlUtil {

    public static String convertToXml(Object obj) {
        return convertToXml(obj, true);
    }

    public static String convertToXml(Object obj, boolean standalone) {
        if (null == obj) {
            return "";
        }
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        if (standalone) {
            return sw.toString().replace("standalone=\"yes\"", "");
        } else {
            return sw.toString();
        }
    }
}