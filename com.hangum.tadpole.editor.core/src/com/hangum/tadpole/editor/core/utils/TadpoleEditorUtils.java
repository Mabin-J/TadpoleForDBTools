/*******************************************************************************
 * Copyright (c) 2013 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.editor.core.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hangum.tadpold.commons.libs.core.define.PublicTadpoleDefine;

/**
 * Tadpole Editor Utils
 * 
 * @author hangum
 *
 */
public class TadpoleEditorUtils {
	private static final Logger logger = Logger.getLogger(TadpoleEditorUtils.class);
	
	/**
	 * Tadpole Editor grant text
	 * 
	 * @param initContent
	 * @return
	 */
	public static String getGrantText(String initContent) {
		String strInitContent = initContent;
		
		try {
			strInitContent = StringUtils.replace(initContent, PublicTadpoleDefine.LINE_SEPARATOR, "\\n");
			strInitContent = StringUtils.replace(strInitContent, "\"", "'");
			
		} catch(Exception e) {
			logger.error("Tadpole Editor grant utils", e);
		}
		
		return strInitContent;
	}
}
