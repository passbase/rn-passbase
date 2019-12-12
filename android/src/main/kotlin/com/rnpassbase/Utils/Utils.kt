package com.rnpassbase.Utils

import com.facebook.react.bridge.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@Throws(JSONException::class)
fun convertJsonToMap(jsonObject: JSONObject): WritableMap? {
    val map: WritableMap = WritableNativeMap()
    val iterator = jsonObject.keys()
    while (iterator.hasNext()) {
        val key = iterator.next()
        val value = jsonObject[key]
        if (value is JSONObject) {
            map.putMap(key, convertJsonToMap(value))
        } else if (value is JSONArray) {
            map.putArray(key, convertJsonToArray(value))
        } else if (value is Boolean) {
            map.putBoolean(key, value)
        } else if (value is Int) {
            map.putInt(key, value)
        } else if (value is Double) {
            map.putDouble(key, value)
        } else if (value is String) {
            map.putString(key, value)
        } else {
            map.putString(key, value.toString())
        }
    }
    return map
}

@Throws(JSONException::class)
fun convertJsonToArray(jsonArray: JSONArray): WritableArray? {
    val array: WritableArray = WritableNativeArray()
    for (i in 0 until jsonArray.length()) {
        val value = jsonArray[i]
        if (value is JSONObject) {
            array.pushMap(convertJsonToMap(value))
        } else if (value is JSONArray) {
            array.pushArray(convertJsonToArray(value))
        } else if (value is Boolean) {
            array.pushBoolean(value)
        } else if (value is Int) {
            array.pushInt(value)
        } else if (value is Double) {
            array.pushDouble(value)
        } else if (value is String) {
            array.pushString(value)
        } else {
            array.pushString(value.toString())
        }
    }
    return array
}

@Throws(JSONException::class)
fun convertMapToJson(readableMap: ReadableMap?): JSONObject? {
    val `object` = JSONObject()
    val iterator = readableMap!!.keySetIterator()
    while (iterator.hasNextKey()) {
        val key = iterator.nextKey()
        when (readableMap.getType(key)) {
            ReadableType.Null -> `object`.put(key, JSONObject.NULL)
            ReadableType.Boolean -> `object`.put(key, readableMap.getBoolean(key))
            ReadableType.Number -> `object`.put(key, readableMap.getDouble(key))
            ReadableType.String -> `object`.put(key, readableMap.getString(key))
            ReadableType.Map -> `object`.put(key, convertMapToJson(readableMap.getMap(key)))
            ReadableType.Array -> `object`.put(key, convertArrayToJson(readableMap.getArray(key)))
        }
    }
    return `object`
}

@Throws(JSONException::class)
fun mapKeysCount(readableMap: ReadableMap?): Int {
    var count = 0
    val iterator = readableMap!!.keySetIterator()
    while (iterator.hasNextKey()) {
        count++
        val key = iterator.nextKey()
    }
    return count
}

@Throws(JSONException::class)
fun convertArrayToJson(readableArray: ReadableArray?): JSONArray? {
    val array = JSONArray()
    for (i in 0 until readableArray!!.size()) {
        when (readableArray.getType(i)) {
            ReadableType.Null -> {
            }
            ReadableType.Boolean -> array.put(readableArray.getBoolean(i))
            ReadableType.Number -> array.put(readableArray.getDouble(i))
            ReadableType.String -> array.put(readableArray.getString(i))
            ReadableType.Map -> array.put(convertMapToJson(readableArray.getMap(i)))
            ReadableType.Array -> array.put(convertArrayToJson(readableArray.getArray(i)))
        }
    }
    return array
}

@Throws(JSONException::class)
fun convertMapToPairArr(readableMap: ReadableMap): Array<Pair<String, String>> {
    var additionalAttribs: Array<Pair<String, String>> = emptyArray()
    val iterator = readableMap!!.keySetIterator()
    var index = 0;
    while (iterator.hasNextKey()) {
        val key = iterator.nextKey()
        val value = readableMap.getString(key)
        if (key != null && value !== null) {
            additionalAttribs = additionalAttribs.plus(Pair(key, readableMap.getString(key)) as Pair<String, String>)
        }
    }
    return additionalAttribs
}
