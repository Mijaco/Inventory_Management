package com.ibcs.desco.procurement.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibcs.desco.common.model.Constrants;

@Controller
@RequestMapping(value = "/notice")
@PropertySource("classpath:common.properties")
public class TenderNoticeController extends Constrants {}
