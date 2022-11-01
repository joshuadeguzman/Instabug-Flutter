package com.instabug.flutter.modules;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.instabug.apm.APM;
import com.instabug.apm.model.ExecutionTrace;
import com.instabug.apm.networking.APMNetworkLogger;
import com.instabug.flutter.generated.ApmPigeon;
import com.instabug.flutter.util.ArgsRegistry;
import com.instabug.flutter.util.Reflection;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;

public class ApmApi implements ApmPigeon.ApmHostApi {
    private final String TAG = ApmApi.class.getName();
    private final HashMap<String, ExecutionTrace> traces = new HashMap<>();

    public static void init(BinaryMessenger messenger) {
        final ApmApi api = new ApmApi();
        ApmPigeon.ApmHostApi.setup(messenger, api);
    }

    @Override
    public void setEnabled(@NonNull Boolean isEnabled) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    APM.setEnabled(isEnabled);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setColdAppLaunchEnabled(@NonNull Boolean isEnabled) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    APM.setAppLaunchEnabled(isEnabled);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setAutoUITraceEnabled(@NonNull Boolean isEnabled) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    APM.setAutoUITraceEnabled(isEnabled);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setLogLevel(@NonNull String level) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ArgsRegistry.getDeserializedValue(level) == null) {
                        return;
                    }
                    APM.setLogLevel((int) ArgsRegistry.getRawValue(level));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Nullable
    @Override
    public String startExecutionTrace(@NonNull String id, @NonNull String name) {
        try {
            String result = null;
            ExecutionTrace trace = APM.startExecutionTrace(name);
            if (trace != null) {
                result = id;
                traces.put(id, trace);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setExecutionTraceAttribute(@NonNull String id, @NonNull String key, @NonNull String value) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    traces.get(id).setAttribute(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void endExecutionTrace(@NonNull String id) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    traces.get(id).end();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void startUITrace(@NonNull String name) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    APM.startUITrace(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void endUITrace() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    APM.endUITrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void endAppLaunch() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    APM.endAppLaunch();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void networkLogAndroid(@NonNull Map<String, Object> data) {
        try {
            APMNetworkLogger apmNetworkLogger = new APMNetworkLogger();
            final String requestUrl = (String) data.get("url");
            final String requestBody = (String) data.get("requestBody");
            final String responseBody = (String) data.get("responseBody");
            final String requestMethod = (String) data.get("method");
            //--------------------------------------------
            final String requestContentType = (String) data.get("requestContentType");
            final String responseContentType = (String) data.get("responseContentType");
            //--------------------------------------------
            final long requestBodySize = ((Number) data.get("requestBodySize")).longValue();
            final long responseBodySize = ((Number) data.get("responseBodySize")).longValue();
            //--------------------------------------------
            final String errorDomain = (String) data.get("errorDomain");
            final Integer statusCode = (Integer) data.get("responseCode");
            final long requestDuration = ((Number) data.get("duration")).longValue() / 1000;
            final long requestStartTime = ((Number) data.get("startTime")).longValue() * 1000;
            final String requestHeaders = (new JSONObject((HashMap<String, String>) data.get("requestHeaders"))).toString(4);
            final String responseHeaders = (new JSONObject((HashMap<String, String>) data.get("responseHeaders"))).toString(4);
            final String errorMessage;

            if (errorDomain.equals("")) {
                errorMessage = null;
            } else {
                errorMessage = errorDomain;
            }
            //--------------------------------------------------
            String gqlQueryName = null;
            if (data.containsKey("gqlQueryName")) {
                gqlQueryName = (String) data.get("gqlQueryName");
            }
            String serverErrorMessage = "";
            if (data.containsKey("serverErrorMessage")) {
                serverErrorMessage = (String) data.get("serverErrorMessage");
            }

            Method method = Reflection.getMethod(Class.forName("com.instabug.apm.networking.APMNetworkLogger"), "log", long.class, long.class, String.class, String.class, long.class, String.class, String.class, String.class, String.class, String.class, long.class, int.class, String.class, String.class, String.class, String.class);
            if (method != null) {
                method.invoke(apmNetworkLogger, requestStartTime, requestDuration, requestHeaders, requestBody, requestBodySize, requestMethod, requestUrl, requestContentType, responseHeaders, responseBody, responseBodySize, statusCode, responseContentType, errorMessage, gqlQueryName, serverErrorMessage);
            } else {
                Log.e(TAG, "APMNetworkLogger.log was not found by reflection");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
