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
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ivianuu.kprefs.KPrefs
import com.ivianuu.kprefs.boolean
import com.ivianuu.kprefs.coroutines.asFlow
import com.ivianuu.kprefs.livedata.asLiveData
import com.ivianuu.kprefs.rx.asObservable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            myPref.asFlow().collect {
                Log.d("Coroutines 1", "on changed -> $it")
            }
        }

        myPref.asFlow()
            .onEach { Log.d("Coroutines 2", "on changed -> $it") }
            .launchIn(GlobalScope)

        myPref.asObservable()
            .subscribe { Log.d("RxJava", "on changed -> $it") }
            .let { disposables.add(it) }

        myPref.asLiveData().observe(this, Observer {
            Log.d("LiveData", "on changed -> $it")
        })

        findViewById<CheckBox>(R.id.checkbox).setOnCheckedChangeListener { _, isChecked ->
            myPref.set(isChecked)
        }
    }

    override fun onDestroy() {
        job.cancel()
        disposables.clear()
        super.onDestroy()
    }
}