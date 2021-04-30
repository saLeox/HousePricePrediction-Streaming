package com.gof.springcloud.etl;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonExtractUtil {

	public static String[] transaction_feature = new String[] { "Id", "MSSubClass", "MSZoning", "LotFrontage",
			"LotArea", "Street", "Alley", "LotShape", "LandContour", "Utilities", "LotConfig", "LandSlope",
			"Neighborhood", "Condition1", "Condition2", "BldgType", "HouseStyle", "OverallQual", "OverallCond",
			"YearBuilt", "YearRemodAdd", "RoofStyle", "RoofMatl", "Exterior1st", "Exterior2nd", "MasVnrType",
			"MasVnrArea", "ExterQual", "ExterCond", "Foundation", "BsmtQual", "BsmtCond", "BsmtExposure",
			"BsmtFinType1", "BsmtFinSF1", "BsmtFinType2", "BsmtFinSF2", "BsmtUnfSF", "TotalBsmtSF", "Heating",
			"HeatingQC", "Electrical", "_1stFlrSF", "_2ndFlrSF", "LowQualFinSF", "GrLivArea",
			"BsmtFullBath", "BsmtHalfBath", "FullBath", "HalfBath", "BedroomAbvGr", "KitchenAbvGr", "KitchenQual",
			"TotRmsAbvGrd", "Functional", "Fireplaces", "FireplaceQu", "GarageType", "GarageYrBlt", "GarageFinish",
			"GarageCars", "GarageArea", "GarageQual", "GarageCond", "PavedDrive", "WoodDeckSF", "OpenPorchSF",
			"EnclosedPorch", "_3SsnPorch", "ScreenPorch", "PoolArea", "PoolQC", "Fence", "MiscFeature", "MiscVal",
			"MoSold", "YrSold", "SaleType", "SaleCondition", "SalePrice" };

	/**
	 * @param str raw Json Str
	 * @param extractColumn the columns needed to be extracted
	 * @return return Map
	 */
	public static Map<String, Object> extractFromStr(String str, String[] extractColumn) {
		Map<String, Object> result = new TreeMap<String, Object>();
		JSONObject obj_raw = null;
		JSONObject obj_extracted = null;
		try {
			obj_raw = new JSONObject(str);
			obj_extracted = new JSONObject(obj_raw, transaction_feature);
		} catch (JSONException e) {
			log.error("Parse Json encouter error: {}", e);
		}
		if (null != obj_extracted) {
			result = new Gson().fromJson(obj_extracted.toString(), TreeMap.class);
		}
		log.info(result.toString());
		return result;
	}
}
