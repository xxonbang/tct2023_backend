package com.lgcns.tct.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsedListDto {
    private String use_no;
    private String use_distance;
    private int use_time;
    private String use_start_dt;
    private String use_end_dt;
    private String pay_datetime;
    private int card_pay;
    private int point_pay;    
}

