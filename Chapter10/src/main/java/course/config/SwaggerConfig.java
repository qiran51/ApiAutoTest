package course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//加载配置文件
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/.*"))
                .build();
    }
    /**
     * 创建该API的基本信息（这些信息会展现在文档页面中）
     * 访问地址：本地地址(localhost):8888/swagger-ui.html
     *
     *
     * */

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("InterfaceDocument")
                .contact(new Contact("liam","#","liwanjing5_1@163.com"))
                .description("Swagger生成接口文档")
                .version("1.0.0.0")
                .build();
    }
}
