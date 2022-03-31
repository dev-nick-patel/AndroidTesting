package com.sample.groovytdd.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import petros.efthymiou.groovy.utils.MainCoroutineScopeRule

open class BaseUnitTest {

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
}