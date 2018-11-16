/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.kprefs.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.coroutines.receiveChannel
import com.ivianuu.kprefs.lifecycle.addListener
import com.ivianuu.kprefs.lifecycle.liveData
import com.ivianuu.kprefs.rx.observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * @author Manuel Wrage (IVIanuu)
 */
class MainActivity : AppCompatActivity() {

    private val prefs by lazy { KPrefs(this) }

    private val myPref by lazy { prefs.boolean("my_pref") }

    private val disposables = CompositeDisposable()

    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(job) {
            myPref.receiveChannel.consumeEach {
                Log.d("Coroutines", "on changed -> $it")
            }
        }

        myPref.observable
            .subscribe { Log.d("RxJava", "on changed -> $it") }
            .apply { disposables.add(this) }

        myPref.liveData.observe(this, Observer {
            Log.d("LiveData", "on changed -> $it")
        })

        myPref.addListener(this) {
            Log.d("Listener", "on changed -> $it")
            current_value.text = "Current value: $it"
            checkbox.isChecked = it
        }

        checkbox.setOnCheckedChangeListener { _, isChecked -> myPref.set(isChecked) }
    }

    override fun onDestroy() {
        job.cancel()
        disposables.clear()
        super.onDestroy()
    }
}