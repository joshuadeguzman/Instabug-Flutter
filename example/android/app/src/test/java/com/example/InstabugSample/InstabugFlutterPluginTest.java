package com.example.InstabugSample;

import org.junit.Test;

public class InstabugFlutterPluginTest {

    /**
     Each Method in this class is preceded with a comment identifying the type of parameters
     that are tested
     */

    /**
     * (String)
     */
    @Test
    public void testShowWelcomeMessageWithMode() {
        new OnMethodCallTests().testShowWelcomeMessageWithMode();
    }

    /**
     * (String, String)
     */
    @Test
    public void testIdentifyUserWithEmail() {
        new OnMethodCallTests().testIdentifyUserWithEmail();
    }

    /**
     * ()
     */
    @Test
    public void testLogOut() {
        new OnMethodCallTests().testLogOut();
    }

    /**
     * (ArrayList<String>)
     */
    @Test
    public void testAppendTags() {
        new OnMethodCallTests().testAppendTags();
    }

    /**
     * (ArrayList<String>)
     */
    @Test
    public void testAddExperiments() {
        new OnMethodCallTests().testAddExperiments();
    }

    /**
     * (ArrayList<String>)
     */
    @Test
    public void testRemoveExperiments() {
        new OnMethodCallTests().testRemoveExperiments();
    }

    /**
     * ()
     */
    @Test
    public void testClearAllExperiments() {
        new OnMethodCallTests().testClearAllExperiments();
    }

    /**
     * (String, ArrayList<String>)
     */
    @Test
    public void testShowBugReportingWithReportTypeAndOptions() {
        new OnMethodCallTests().testShowBugReportingWithReportTypeAndOptions();
    }

    /**
     * (boolean)
     */
    @Test
    public void testSetSessionProfilerEnabled() {
        new OnMethodCallTests().testSetSessionProfilerEnabled();
    }

    /**
     * (boolean)
     */
    @Test
    public void testSetDebugEnabled() {
        new OnMethodCallTests().testSetDebugEnabled();
    }

    /**
     * (long)
     */
    @Test
    public void testSetPrimaryColor() {
        new OnMethodCallTests().testSetPrimaryColor();
    }

    /**
     * (String, int)
     */
    @Test
    public void testSetFloatingButtonEdge() {
        new OnMethodCallTests().testSetFloatingButtonEdge();
    }

    /**
     * (byte[], String)
     */
    @Test
    public void testAddFileAttachmentWithData() {
        new OnMethodCallTests().testAddFileAttachmentWithData();
    }

    /**
     * (boolean, boolean, boolean, boolean)
     */
    @Test
    public void testSetEnabledAttachmentTypes() {
        new OnMethodCallTests().testSetEnabledAttachmentTypes();
    }

    /**
     * (boolean, ArrayList<String>)
     */
    @Test
    public void testSetEmailFieldRequiredForFeatureRequests() {
        new OnMethodCallTests().testSetEmailFieldRequiredForFeatureRequests();
    }
}
