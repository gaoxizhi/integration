package net.gaox.thread.production;

/**
 * <p> 组装产品的说明书 抽象类（模版方法） </p>
 * 在流水线上需要被加工的产品，create作为一个模板方法，提供了加工产品的说明书
 *
 * @author gaox·Eric
 * @date 2024-01-30 17:51
 */
public abstract class AbstractInstructionBook {

    /**
     * 产品编号
     */
    private final int prodId;

    public AbstractInstructionBook(int prodId) {
        this.prodId = prodId;
    }

    /**
     * 生产产品
     */
    public final void create() {
        this.firstProcess();
        this.secondProcess();
    }

    public int getProdId() {
        return prodId;
    }

    /**
     * 加工产品步骤1
     */
    protected abstract void firstProcess();

    /**
     * 加工产品步骤2
     */
    protected abstract void secondProcess();

}
