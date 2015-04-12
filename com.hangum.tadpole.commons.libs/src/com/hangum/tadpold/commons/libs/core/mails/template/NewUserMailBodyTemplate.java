/*******************************************************************************
 * Copyright (c) 2014 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpold.commons.libs.core.mails.template;

/**
 * new user mail template
 * 
 * @author hangum
 *
 */
public class NewUserMailBodyTemplate extends MailBodyTemplate {

	/**
	 * 
	 * @param strName
	 * @param strEmail
	 * @param strConfirmKey
	 * @return
	 */
	public String getContent(String strName, String strEmail, String strConfirmKey) {
		StringBuffer strContent = new StringBuffer("<html>");
		
		strContent.append(makeHead("Tadpole DB Hub user has been added.\n Please check."));
		strContent.append("<table border='1' cellpadding='0' cellspacing='0' width='100%'>");
				
			strContent.append("<tr>");
				strContent.append("<td width='100' valign='top'>");
				strContent.append("User Name");
				strContent.append("</td>");
				strContent.append("<td width='260' valign='top'>");
				strContent.append(strName);
				strContent.append("</td>");
			strContent.append("</tr>");
	
			strContent.append("<tr>");
				strContent.append("<td width='100' valign='top'>");
				strContent.append("Email");
				strContent.append("</td>");
				strContent.append("<td width='260' valign='top'>");
				strContent.append(strEmail);
				strContent.append("</td>");
			strContent.append("</tr>");
		
			strContent.append("<tr>");
			strContent.append("<td width='100' valign='top'>");
			strContent.append("Confirm key");
			strContent.append("</td>");
			strContent.append("<td width='260' valign='top'><b>");
			strContent.append(strConfirmKey);
			strContent.append("</b></td>");
		strContent.append("</tr>");
		
		strContent.append("</table>");
		
		
		strContent.append(makeTail());
	
		//
		strContent.append("</html>");

		return strContent.toString();
	}
}
