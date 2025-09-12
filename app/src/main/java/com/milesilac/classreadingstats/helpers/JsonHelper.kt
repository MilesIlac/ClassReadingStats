package com.milesilac.classreadingstats.helpers

import kotlinx.serialization.json.Json

inline fun <reified T> T.transformToJsonString() = Json.encodeToString(value = this)

inline fun <reified T> String.transformFromJsonString(): T = Json.decodeFromString<T>(string = this)