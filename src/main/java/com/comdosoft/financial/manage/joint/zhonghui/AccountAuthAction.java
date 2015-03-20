package com.comdosoft.financial.manage.joint.zhonghui;

import java.io.File;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 账户认证
 * @author wu
 *
 */
public class AccountAuthAction extends RequireLoginAction {
	private String bankDeposit;
	private String accountNo;
	private String name;
	private String unionBankNo;
	private File card;

	public AccountAuthAction(String phoneNum, String password, String position,
			String bankDeposit, String accountNo, String name,
			String unionBankNo, File card) {
		super(phoneNum, password, position);
		this.bankDeposit = bankDeposit;
		this.accountNo = accountNo;
		this.name = name;
		this.unionBankNo = unionBankNo;
		this.card = card;
	}

	@Override
	protected Map<String, String> params() {
		Map<String, String> params = createParams();
		params.put("name", name);
		params.put("bankDeposit", bankDeposit);
		params.put("accountNo", accountNo);
		params.put("unionBankNo", unionBankNo);
		return params;
	}

	@Override
	protected Map<String, File> fileParams() {
		Map<String, File> params = Maps.newHashMap();
		params.put("card", card);
		return params;
	}

	@Override
	public String url() {
		return "/user/accountAuth";
	}

	@Override
	protected Class<? extends Result> getResultType() {
		return Result.class;
	}

}
