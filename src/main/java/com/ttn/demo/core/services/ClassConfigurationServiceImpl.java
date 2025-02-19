package com.ttn.demo.core.services;



import java.util.List;

import com.ttn.demo.core.config.ClassConfiguration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

@Component(
        service = ClassConfigurationService.class,
        configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(ocd = ClassConfiguration.class)
public class ClassConfigurationServiceImpl implements ClassConfigurationService {

    private int studentLimit;
    private int passingMarks;

    @Activate
    @Modified
    protected void activate(ClassConfiguration config) {
        this.studentLimit = config.studentLimit();
        this.passingMarks = config.passingMarks();
    }


    @Override
    public boolean isClassLimitReached(List<?> students) {
        // Per the assignment: returns true if the list size is less than the allowed size.
        return students.size() < studentLimit;
    }

    @Override
    public int getPassingMarks() {
        return passingMarks;
    }
}

