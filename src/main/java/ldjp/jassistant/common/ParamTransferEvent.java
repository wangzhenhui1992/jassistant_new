package ldjp.jassistant.common;

import java.util.EventObject;

/**
 *
 */
public class ParamTransferEvent extends EventObject {

	/**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
	 * result object
	 */
	private Object param = null;

	/**
	 * option flag
	 */
	private int opFlag = 0;

	public ParamTransferEvent(Object source) {
		super(source);
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public int getOpFlag() {
		return opFlag;
	}

	public void setOpFlag(int opFlag) {
		this.opFlag = opFlag;
	}
}
