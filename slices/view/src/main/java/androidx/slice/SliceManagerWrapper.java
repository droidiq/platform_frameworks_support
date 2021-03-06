/*
 * Copyright 2018 The Android Open Source Project
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

package androidx.slice;

import static androidx.slice.SliceConvert.unwrap;
import static androidx.slice.widget.SliceLiveData.SUPPORTED_SPECS;

import android.app.slice.SliceSpec;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
@RequiresApi(api = 28)
class SliceManagerWrapper extends SliceManagerBase {

    private final android.app.slice.SliceManager mManager;
    private final List<SliceSpec> mSpecs;

    SliceManagerWrapper(Context context) {
        this(context, context.getSystemService(android.app.slice.SliceManager.class));
    }

    SliceManagerWrapper(Context context, android.app.slice.SliceManager manager) {
        super(context);
        mManager = manager;
        mSpecs = new ArrayList<>(unwrap(SUPPORTED_SPECS));
    }

    @Override
    public void pinSlice(@NonNull Uri uri) {
        mManager.pinSlice(uri, mSpecs);
    }

    @Override
    public void unpinSlice(@NonNull Uri uri) {
        mManager.unpinSlice(uri);
    }

    @Override
    public @NonNull Set<androidx.slice.SliceSpec> getPinnedSpecs(@NonNull Uri uri) {
        return SliceConvert.wrap(mManager.getPinnedSpecs(uri));
    }

    @Nullable
    @Override
    public androidx.slice.Slice bindSlice(@NonNull Uri uri) {
        return SliceConvert.wrap(mManager.bindSlice(uri, mSpecs));
    }

    @Nullable
    @Override
    public androidx.slice.Slice bindSlice(@NonNull Intent intent) {
        return SliceConvert.wrap(mManager.bindSlice(intent, mSpecs));
    }

    @Override
    public Collection<Uri> getSliceDescendants(Uri uri) {
        return mManager.getSliceDescendants(uri);
    }

    @Nullable
    @Override
    public Uri mapIntentToUri(@NonNull Intent intent) {
        return mManager.mapIntentToUri(intent);
    }
}
