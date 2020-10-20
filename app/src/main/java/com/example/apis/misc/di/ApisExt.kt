package com.example.apis.misc.di

import org.koin.core.KoinComponent
import org.koin.core.context.KoinContextHandler
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> ApiComponent.inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy(LazyThreadSafetyMode.NONE) { get<T>(qualifier, parameters) }

inline fun <reified T : Any> ApiComponent.get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T = getKoin().get(qualifier, parameters)

fun ApiComponent.getKoin() = when (this) {
    is KoinComponent -> this.getKoin()
    else -> KoinContextHandler.get()
}

interface ApiComponent