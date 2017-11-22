package paasta.delivery.pipeline.inspection.api.codingRules;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import paasta.delivery.pipeline.inspection.api.common.CommonService;
import paasta.delivery.pipeline.inspection.api.common.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Coding rules Service.
 */
@Service
public class CodingRulesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodingRulesService.class);

    @Autowired
    private CommonService commonService;

    /**
     * The Delivery server url.
     */
    @Value("${inspection.server.url}")
    public String inspectionServerUrl;


    /**
     * Gets coding rules.
     *
     * @param codingRules the coding rules
     * @return the coding rules
     */
    public CodingRules getCodingRules(CodingRules codingRules) {

        // /api/rules/search?activation=true&f=name&facets=active_severities&ps=1&qprofile={profileKey}
        String reqUrl = commonService.makeQueryParam(Constants.API_RULES_SEARCH, codingRules);

        LOGGER.info("===[INSPECTION-API :: getCodingRules]=== reqUrl : {}", reqUrl);

        CodingRules data = commonService.sendForm(inspectionServerUrl, reqUrl, HttpMethod.GET, null, CodingRules.class);

        data.setResultStatus(Constants.RESULT_STATUS_SUCCESS);

        return data;

    }




    //TODO --------------------------


    /**
     * Get coding rules deteil map.
     *
     * @param codingRules the coding rules
     * @return the map
     */
    Map getCodingRulesDeteil(CodingRules codingRules){
        Object resultJson = commonService.sendForm(inspectionServerUrl, "/api/rules/show?key="+codingRules.getKey()+"&actives="+codingRules.getActives(), HttpMethod.GET , null,Object.class);
        Map <String, Object> objectHashMap = new HashMap<>();
        objectHashMap = (Map)resultJson;
        return objectHashMap;

    }


}

