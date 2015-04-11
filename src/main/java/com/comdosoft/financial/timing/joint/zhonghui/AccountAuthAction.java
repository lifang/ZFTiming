package com.comdosoft.financial.timing.joint.zhonghui;

import java.io.InputStream;
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
	private InputStream card;

	public AccountAuthAction(String phoneNum, String password, String position, String appVersion,
			String bankDeposit, String accountNo, String name,
			String unionBankNo, InputStream card) {
		super(phoneNum, password, position, appVersion);
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
	protected Map<String, InputStream> fileParams() {
		Map<String, InputStream> params = Maps.newHashMap();
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

	@Override
	protected int checkIndex() {
		return 2;
	}

}
