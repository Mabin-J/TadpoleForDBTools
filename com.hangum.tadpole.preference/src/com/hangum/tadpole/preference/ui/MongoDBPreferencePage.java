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
package com.hangum.tadpole.preference.ui;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.hangum.tadpole.commons.google.analytics.AnalyticCaller;
import com.hangum.tadpole.commons.libs.core.define.PublicTadpoleDefine;
import com.hangum.tadpole.commons.libs.core.message.CommonMessages;
import com.hangum.tadpole.engine.query.sql.TadpoleSystem_UserInfoData;
import com.hangum.tadpole.preference.Messages;
import com.hangum.tadpole.preference.define.PreferenceDefine;
import com.hangum.tadpole.preference.get.GetPreferenceGeneral;
import com.hangum.tadpole.session.manager.SessionManager;

/** 
 * mongodb prefernec page
 * 
 *  @author hangum
 */
public class MongoDBPreferencePage extends TadpoleDefaulPreferencePage implements IWorkbenchPreferencePage {
	private static final Logger logger = Logger.getLogger(MongoDBPreferencePage.class);
	
	private Text textLimitCount;
	private Text textMaxCount;
	
	// find page
	private Button btnBasicSearch;
//	private Button btnExtendSearch;
	
	// result page
	private Button btnTreeView;
	private Button btnTableView;
	
	public MongoDBPreferencePage() {
	}

	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {

		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText(Messages.get().MongoDBPreferencePage_0);
		
		textLimitCount = new Text(container, SWT.BORDER);
		textLimitCount.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				isValid();
			}
		});
		textLimitCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setText(Messages.get().MongoDBPreferencePage_1);
		
		textMaxCount = new Text(container, SWT.BORDER);
		textMaxCount.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				isValid();
			}
		});
		textMaxCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_2 = new Label(container, SWT.NONE);
		lblNewLabel_2.setText(Messages.get().MongoDBPreferencePage_2);
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnBasicSearch = new Button(composite, SWT.RADIO);
		btnBasicSearch.setText(Messages.get().MongoDBPreferencePage_3);
		btnBasicSearch.setData(PreferenceDefine.MONGO_DEFAULT_FIND_BASIC);
		new Label(composite, SWT.NONE);
		
//		btnExtendSearch = new Button(composite, SWT.RADIO);
//		btnExtendSearch.setText("Extend Search");
//		btnExtendSearch.setData(PreferenceDefine.MONGO_DEFAULT_FIND_EXTEND);
		
		Label lblResultPage = new Label(container, SWT.NONE);
		lblResultPage.setText(Messages.get().MongoDBPreferencePage_4);
		
		Composite composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayout(new GridLayout(2, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnTreeView = new Button(composite_1, SWT.RADIO);
		btnTreeView.setText(Messages.get().MongoDBPreferencePage_5);
		btnTreeView.setData(PreferenceDefine.MONGO_DEFAULT_RESULT_TREE);
		
		btnTableView = new Button(composite_1, SWT.RADIO);
		btnTableView.setText(Messages.get().MongoDBPreferencePage_6);
		btnTableView.setData(PreferenceDefine.MONGO_DEFAULT_RESULT_TABLE);
		
		initDefaultValue();
		
		// google analytic
		AnalyticCaller.track(this.getClass().getName());
		
		return container;
	}
	
	@Override
	public boolean isValid() {
		String txtLimitCount = textLimitCount.getText();
		String txtMaxCount = textMaxCount.getText();
		if(!NumberUtils.isNumber(txtLimitCount)) {
			textLimitCount.setFocus();
			
			setValid(false);
			setErrorMessage(Messages.get().MongoDBPreferencePage_10);
			return false;
		} else if(!(NumberUtils.toInt(txtLimitCount) >= 30 && NumberUtils.toInt(txtLimitCount) <= 10000)) {
			textLimitCount.setFocus();

			setValid(false);
			setErrorMessage(String.format(CommonMessages.get().ValueIsLessThanOrOverThan, Messages.get().MongoDBPreferencePage_0, "30", "10,000"));
			return false;
		} else if(!NumberUtils.isNumber(txtMaxCount)) {
			textMaxCount.setFocus();
			
			setValid(false);
			setErrorMessage(Messages.get().MongoDBPreferencePage_10);
			return false;
		} else if(!(NumberUtils.toInt(txtMaxCount) >= 200 && NumberUtils.toInt(txtMaxCount) <= 10000)) {
			textMaxCount.setFocus();
			
			setValid(false);
			setErrorMessage(String.format(CommonMessages.get().ValueIsLessThanOrOverThan, Messages.get().MongoDBPreferencePage_1, "200", "10,000"));
			return false;
		}
		
		setErrorMessage(null);
		setValid(true);
		
		return true;
	}
	
	@Override
	public boolean performOk() {
		if(PublicTadpoleDefine.YES_NO.NO.name().equals(SessionManager.getIsModifyPerference())) {
			MessageDialog.openWarning(null, CommonMessages.get().Warning, CommonMessages.get().CantModifyPreferenc);
			return false;
		}
		
		if(!isValid()) return false;
		
		String txtLimitCount = textLimitCount.getText();
		String txtMaxCount = textMaxCount.getText();
		String txtFindPage = ""; //$NON-NLS-1$
		String txtResultPage = ""; //$NON-NLS-1$
		
		if(!NumberUtils.isNumber(txtLimitCount)) {
			MessageDialog.openWarning(getShell(), CommonMessages.get().Warning, Messages.get().MongoDBPreferencePage_10);			 //$NON-NLS-1$
			textLimitCount.setFocus();
			return false;
		} else if(!(NumberUtils.toInt(txtLimitCount) >= 30 && NumberUtils.toInt(txtLimitCount) <= 10000)) {
			textLimitCount.setFocus();
			MessageDialog.openWarning(getShell(),CommonMessages.get().Warning, String.format(CommonMessages.get().ValueIsLessThanOrOverThan, Messages.get().MongoDBPreferencePage_0, "30", "10,000"));			 //$NON-NLS-1$
			return false;
		} else if(!NumberUtils.isNumber(txtMaxCount)) {
			MessageDialog.openWarning(getShell(), CommonMessages.get().Warning, Messages.get().MongoDBPreferencePage_10);			 //$NON-NLS-1$
			textMaxCount.setFocus();
			return false;
		} else if(!(NumberUtils.toInt(txtMaxCount) >= 200 && NumberUtils.toInt(txtMaxCount) <= 10000)) {
			textMaxCount.setFocus();
			MessageDialog.openWarning(getShell(),CommonMessages.get().Warning, String.format(CommonMessages.get().ValueIsLessThanOrOverThan, Messages.get().MongoDBPreferencePage_1, "200", "10,000"));			 //$NON-NLS-1$
			return false;
		}

//		if(btnBasicSearch.getSelection()) {
			txtFindPage = btnBasicSearch.getData().toString();
//		} else {
//			txtFindPage = btnExtendSearch.getData().toString();
//		}
		
		if(btnTreeView.getSelection()) {
			txtResultPage = btnTreeView.getData().toString();
		} else if(btnTableView.getSelection()) {
			txtResultPage = btnTableView.getData().toString();
		}
		
		// 테이블에 저장 
		try {
			TadpoleSystem_UserInfoData.updateMongoDBUserInfoData(txtLimitCount, txtMaxCount, txtFindPage, txtResultPage);
			
			// session 데이터를 수정한다.
			SessionManager.setUserInfo(PreferenceDefine.MONGO_DEFAULT_LIMIT, txtLimitCount);
			SessionManager.setUserInfo(PreferenceDefine.MONGO_DEFAULT_MAX_COUNT, txtMaxCount);
			SessionManager.setUserInfo(PreferenceDefine.MONGO_DEFAULT_FIND, txtFindPage);
			SessionManager.setUserInfo(PreferenceDefine.MONGO_DEFAULT_RESULT, txtResultPage);
		} catch(Exception e) {
			logger.error("MongoDBreference saveing", e);
			
			MessageDialog.openError(getShell(), CommonMessages.get().Confirm, e.getMessage());
			return false;
		}
		
		return super.performOk();
	}

	@Override
	public boolean performCancel() {
		initDefaultValue();
		
		return super.performCancel();
	}
	
	@Override
	protected void performApply() {

		super.performApply();
	}
	
	@Override
	protected void performDefaults() {
		
		initDefaultValue();

		super.performDefaults();
	}
	
	/**
	 * 초기값을 설정 합니다.
	 */
	private void initDefaultValue() {
		btnBasicSearch.setSelection(false);
//		btnExtendSearch.setSelection(false);
		btnTreeView.setSelection(false);
		btnTableView.setSelection(false);
		
		textLimitCount.setText( "" + GetPreferenceGeneral.getMongoDefaultLimit() ); //$NON-NLS-1$
		textMaxCount.setText( "" + GetPreferenceGeneral.getMongoDefaultMaxCount() ); //$NON-NLS-1$
//		if(PreferenceDefine.MONGO_DEFAULT_FIND_BASIC.equals( GetPreferenceGeneral.getMongoDefaultFindPage() )) {
			btnBasicSearch.setSelection(true);
//		} else {
//			btnExtendSearch.setSelection(true);
//		}
		
		if(PreferenceDefine.MONGO_DEFAULT_RESULT_TREE.equals(GetPreferenceGeneral.getMongoDefaultResultPage() )) {
			btnTreeView.setSelection(true);
		} else if(PreferenceDefine.MONGO_DEFAULT_RESULT_TABLE.equals(GetPreferenceGeneral.getMongoDefaultResultPage() )) {
			btnTableView.setSelection(true);
		}
		
	}
}
