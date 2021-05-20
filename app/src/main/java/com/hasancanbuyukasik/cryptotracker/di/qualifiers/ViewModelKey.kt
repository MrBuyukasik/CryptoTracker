package com.hasancanbuyukasik.cryptotracker.di.qualifiers

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

//Make Injectable ViewModel with Dagger
//Instead of creating an annotation for every ViewModel, i build generic annotation structure.
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)