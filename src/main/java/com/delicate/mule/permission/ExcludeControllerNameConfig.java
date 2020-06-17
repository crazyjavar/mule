package com.delicate.mule.permission;//package com.devops.minisystem.permission;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * TODO : 排除controller 有时间实现配置化读取
// *
// * @author caiji Mr. Li
// * @date 2020/6/6 11:23
// */
//@Configuration
//@ConfigurationProperties(prefix = "m")
//public class ExcludeControllerNameConfig {
//
//    private List<String> excludedControllers;
//
//    public Set<String> excludedControllerNames = buildExcludedMethodName();
//
//    private Set<String> buildExcludedMethodName() {
//        Set<String> ControllerNames = new HashSet<String>();
//        for (String controller : excludedControllers) {
//            ControllerNames.add(controller);
//        }
//        return ControllerNames;
//    }
//}
