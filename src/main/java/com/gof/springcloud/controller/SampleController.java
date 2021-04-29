package com.gof.springcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gof.springcloud.bigquery.BigQueryInsertService;
import com.gof.springcloud.entity.ResultVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("sample control")
public class SampleController {
	private Logger log = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private BigQueryInsertService bigQueryInsertService;

	@GetMapping("/bigQueryInsert")
	@ApiOperation(value = "BigQuery Insert")
	public ResultVo<String> bigQueryInsert(){
		Map<String, Object> rowContent = new HashMap<>();
		rowContent.put("Id", "1876986");
		rowContent.put("MSSubClass", "0.235294117647058");
		rowContent.put("MSZoning", "0.75");
		rowContent.put("Neighborhood", "0.636363636363636");
		rowContent.put("OverallQual", "0.666666666666666");
		rowContent.put("YearRemodAdd", "0.0983606557377049");
		rowContent.put("RoofStyle", "0");
		rowContent.put("BsmtQual", "0.75");
		rowContent.put("BsmtExposure", "0.25");
		rowContent.put("HeatingQC", "1");
		rowContent.put("CentralAir", "1");
		rowContent.put("_1stFlrSF", "0.356154748204382");
		rowContent.put("GrLivArea", "0.577712288226008");
		rowContent.put("BsmtFullBath", "0.333333333333333");
		rowContent.put("KitchenQual", "0.666666666666666");
		rowContent.put("Fireplaces", "0");
		rowContent.put("FireplaceQu", "0.2");
		rowContent.put("GarageType", "0.8");
		rowContent.put("GarageFinish", "0.666666666666666");
		rowContent.put("GarageCars", "0.5");
		rowContent.put("PavedDrive", "1");
		rowContent.put("SaleCondition", "0.75");
		rowContent.put("SalePrice", "12.2476943202209");
		bigQueryInsertService.insert(rowContent);
		return new ResultVo<String>("test", true);
	}

	@PostMapping("/postSample")
	@ApiOperation(value = "Add a travelPlan", notes = "Add a travelPlan")
	public ResultVo<String> postSample(){
		return new ResultVo<String>(true);
	}

	@GetMapping
	@ApiOperation(value = "Get sample")
	public ResultVo<String> getSample(){
		return new ResultVo<String>("test", true);
	}

}
