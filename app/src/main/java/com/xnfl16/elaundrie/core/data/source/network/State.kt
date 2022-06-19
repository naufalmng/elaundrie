package com.xnfl16.elaundrie.core.data.source.network

enum class State {
    LOADING,
    SUCCESS,
    FAILED,
    INSERT_SUCCESS,
    UPDATE_SUCCESS,
    DELETE_SUCCESS,
    INSERT_FAILED,
    UPDATE_FAILED,
    DELETE_FAILED,
    DELETE_ALL_SUCCESS,
    DELETE_ALL_FAILED
}