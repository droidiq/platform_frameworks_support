/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.arch.background.workmanager.firebase;

import android.arch.background.workmanager.ExecutionListener;
import android.arch.background.workmanager.Processor;
import android.arch.background.workmanager.Scheduler;
import android.arch.background.workmanager.WorkDatabase;
import android.arch.background.workmanager.WorkerWrapper;
import android.content.Context;
import android.support.annotation.RestrictTo;

import java.util.concurrent.Executors;

/**
 * A {@link Processor} that handles execution for work coming via
 * {@link com.firebase.jobdispatcher.FirebaseJobDispatcher}.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class FirebaseJobProcessor extends Processor {

    private ExecutionListener mOuterListener;

    public FirebaseJobProcessor(
            Context appContext,
            WorkDatabase workDatabase,
            Scheduler scheduler,
            ExecutionListener outerListener) {
        // TODO(sumir): Be more intelligent about the executor.
        super(appContext, workDatabase, scheduler, Executors.newScheduledThreadPool(1));
        mOuterListener = outerListener;
    }

    @Override
    public void onExecuted(String workSpecId, @WorkerWrapper.ExecutionResult int result) {
        super.onExecuted(workSpecId, result);
        if (mOuterListener != null) {
            mOuterListener.onExecuted(workSpecId, result);
        }
    }
}
