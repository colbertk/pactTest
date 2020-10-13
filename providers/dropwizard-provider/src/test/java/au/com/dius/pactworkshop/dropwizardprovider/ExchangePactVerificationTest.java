package au.com.dius.pactworkshop.dropwizardprovider;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.target.Target;
import au.com.dius.pact.provider.junitsupport.target.TestTarget;
import io.dropwizard.testing.ResourceHelpers;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runner.BeforeClass;

@RunWith(PactRunner.class) // Say JUnit to run tests with custom Runner
    @Provider("Exchange") // Set up name of tested provider
    @PactFolder("pacts") // Point where to find pacts (See also section Pacts source in documentation)
    public class ExchangePactVerificationTest {
        // NOTE: this is just an example of embedded service that listens to requests, you should start here real service
        @ClassRule //Rule will be applied once: before/after whole contract test suite
        public static final ExchangeEndToEndTest e = new ExchangeEndToEndTest();

        @BeforeClass //Method will be run once: before whole contract test suite
        public static void setUpService() {
            //Run DB, create schema
            //Run service
            //...
        }

        @org.junit.BeforeClass
        public void before() {
            // Mock dependent service responses
            e.addExpectation(
                    onRequestTo("/auction"), giveEmptyResponse()
            );
        }

        @State("default", "no-data") // Method will be run before testing interactions that require "default" or "no-data" state
        public void toDefaultState() {
            // Prepare service before interaction that require "default" state
            // ...
            System.out.println("Now service in default state");
        }
        
        @State("with-data") // Method will be run before testing interactions that require "with-data" state
        public void toStateWithData(Map data) {
            // Prepare service before interaction that require "with-data" state. The provider state data will be passed 
            // in the data parameter
            // ...
            System.out.println("Now service in state using data " + data);
        }

        @TestTarget // Target for tests - will play pacts as http request and assert response from service by matching rules from pact
        public final Target target = new HttpTarget(8080); 
    }