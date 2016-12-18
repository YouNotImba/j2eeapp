package com.j2ee.ui.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

/**
 * @author younotimba
 *
 */
public class UiUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3846645855732345366L;

	private int viewLoadCount = 0;

	public void greetOnViewLoad(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		if (viewLoadCount < 1 && !context.isPostback()) {
			String firstName = (String) event.getComponent().getAttributes().get("firstName");
			String lastName = (String) event.getComponent().getAttributes().get("lastName");

			FacesMessage message = new FacesMessage(
					String.format("Welcome to your account %s %s", firstName, lastName));
			context.addMessage("growlMessages", message);

			viewLoadCount++;
		}
	}
}
