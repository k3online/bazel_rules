package tests.osgi;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component(service= SampleService.class)
class SampleServiceImpl implements SampleService {

    @Activate
    void start() {

    }
}