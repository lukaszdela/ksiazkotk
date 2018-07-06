package lukks.eu.ksiazkotk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String myExternalFilePath = "file:///C:/coversnew";

        registry.addResourceHandler("/coversnew/**")
                .addResourceLocations("file:coversnew/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        registry.addResourceHandler("/covers/**")
                .addResourceLocations("classpath:/static/covers/");

//        registry.addResourceHandler("/coversnew/**")
//                .addResourceLocations("/coversnew/");

        registry.addResourceHandler("/avatars/**")
                .addResourceLocations("classpath:/static/avatars/");
    }
}
