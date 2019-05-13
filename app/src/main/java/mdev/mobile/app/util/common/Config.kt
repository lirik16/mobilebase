package mdev.mobile.app.util.common

import mdev.mobile.app.BuildConfig

const val DEVELOPER_MODE = BuildConfig.BUILD_TYPE != "release"
const val DEBUG_MODE = BuildConfig.BUILD_TYPE == "debug"
const val CI_BUILD_MODE = BuildConfig.IS_CI_BUILD
