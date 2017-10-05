package ldjp.jassistant.common;

import java.util.EventListener;

/**
 *
 *
 *
 */
public interface ParamTransferListener extends EventListener {

	/**
	 * event of returning results
	 *
	 */
	public void paramTransfered(ParamTransferEvent pe);

}
