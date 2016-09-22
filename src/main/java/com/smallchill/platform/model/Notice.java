package com.smallchill.platform.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

@Table(name = "tb_tfw_tzgg")
@BindID(name = "f_it_xl")
@SuppressWarnings("serial")
public class Notice extends BaseModel {
	private String f_it_xl;
	private Integer f_it_cjr;
	private Integer f_it_lx;
	private Integer f_it_tp;
	private String f_tx_nr;
	private String f_vc_bt;
	private Date f_dt_cjsj;
	private Date f_dt_fbsj;
	private Integer version;

	@AutoID
	@SeqID(name = "SEQ_NOTICE")
	public String getF_it_xl() {
		return f_it_xl;
	}

	public void setF_it_xl(String f_it_xl) {
		this.f_it_xl = f_it_xl;
	}

	public Integer getF_it_cjr() {
		return f_it_cjr;
	}

	public void setF_it_cjr(Integer f_it_cjr) {
		this.f_it_cjr = f_it_cjr;
	}

	public Integer getF_it_lx() {
		return f_it_lx;
	}

	public void setF_it_lx(Integer f_it_lx) {
		this.f_it_lx = f_it_lx;
	}

	public Integer getF_it_tp() {
		return f_it_tp;
	}

	public void setF_it_tp(Integer f_it_tp) {
		this.f_it_tp = f_it_tp;
	}

	public String getF_tx_nr() {
		return f_tx_nr;
	}

	public void setF_tx_nr(String f_tx_nr) {
		this.f_tx_nr = f_tx_nr;
	}

	public String getF_vc_bt() {
		return f_vc_bt;
	}

	public void setF_vc_bt(String f_vc_bt) {
		this.f_vc_bt = f_vc_bt;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getF_dt_cjsj() {
		return f_dt_cjsj;
	}

	public void setF_dt_cjsj(Date f_dt_cjsj) {
		this.f_dt_cjsj = f_dt_cjsj;
	}

	public Date getF_dt_fbsj() {
		return f_dt_fbsj;
	}

	public void setF_dt_fbsj(Date f_dt_fbsj) {
		this.f_dt_fbsj = f_dt_fbsj;
	}

}
