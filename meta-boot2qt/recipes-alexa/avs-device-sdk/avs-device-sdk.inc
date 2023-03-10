#
#   Copyright (c) 2019 Amazon.com, Inc.
#   Copyright (c) 2019 Luxoft Sweden AB
#
#   SPDX-License-Identifier: Apache-2.0
#

SUMMARY = "An SDK for commercial device makers to integrate Alexa directly into connected products."
HOMEPAGE = "https://developer.amazon.com/avs/sdk"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d92e60ee98664c54f68aa515a6169708"

DEPENDS = "curl nghttp2 sqlite3 openssl gstreamer1.0 gstreamer1.0-plugins-base"

RDEPENDS:${PN} += "${PN}-sampleapp"

SRC_URI = "https://github.com/alexa/avs-device-sdk/archive/v${PV}.tar.gz"

# Skip checksum verification
BB_STRICT_CHECKSUM = "0"

# Default extra variables
AAC_BUILD_TYPE = "${@['RELEASE', 'DEBUG'][d.getVar('DEBUG_BUILD') == '1']}"
AAC_SENSITIVE_LOGS = "OFF"
AAC_LATENCY_LOGS = "OFF"
AAC_ENABLE_TESTS = "OFF"

inherit pkgconfig cmake

EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=${AAC_BUILD_TYPE} \
                  -DACSDK_EMIT_SENSITIVE_LOGS=${AAC_SENSITIVE_LOGS} \
                  -DACSDK_LATENCY_LOG=${AAC_LATENCY_LOGS} \
                  -DGSTREAMER_MEDIA_PLAYER=ON \
                 "

PACKAGES += "${PN}-sampleapp"

FILES_${PN}-sampleapp = "${bindir}/SampleApp"
FILES_${PN} = "${libdir}"
FILES_${PN}-dev = "${includedir}"

# Portaudio for microphone input
PORTAUDIO_CMAKE_OPTIONS = "\
  -DPORTAUDIO=ON \
  -DPORTAUDIO_LIB_PATH=${STAGING_DIR_HOST}${libdir}/libportaudio.so \
  -DPORTAUDIO_INCLUDE_DIR=${STAGING_DIR_HOST}${includedir} \
"

PACKAGECONFIG[portaudio] = "${PORTAUDIO_CMAKE_OPTIONS},,portaudio-v19,portaudio-v19"

# WakeWord: KITT.AI
KITTAT_CMAKE_OPTIONS = "\
  -DKITTAI_KEY_WORD_DETECTOR=ON \
  -DKITTAI_KEY_WORD_DETECTOR_LIB_PATH=${STAGING_DIR_HOST}${libdir}/libsnowboy-detect.a \
  -DKITTAI_KEY_WORD_DETECTOR_INCLUDE_DIR=${STAGING_DIR_HOST}${includedir} \
"

PACKAGECONFIG[kittai] = "${KITTAT_CMAKE_OPTIONS},,snowboy openblas,"

# WakeWord: Sensory
SENSORY_CMAKE_OPTIONS = "\
  -DSENSORY_KEY_WORD_DETECTOR=ON \
  -DSENSORY_KEY_WORD_DETECTOR_LIB_PATH=${STAGING_DIR_HOST}${libdir}/libsnsr.a \
  -DSENSORY_KEY_WORD_DETECTOR_INCLUDE_DIR=${STAGING_DIR_HOST}${includedir} \
"
PACKAGECONFIG[sensory] = "${SENSORY_CMAKE_OPTIONS},,truly-handsfree"

# Opus encoding (v1.11 or later)
PACKAGECONFIG[opus] = "-DOPUS=ON,,libopus"

do_install:append() {
    cp -R ${S}/ThirdParty/rapidjson/rapidjson-1.1.0/include/* ${D}${includedir}/include
    install -d ${D}${libdir}

    # Copy the Mock headers from AVS SDK
    mkdir -p ${D}${includedir}/AVSCommon/SDKInterfaces/test
    cp -R ${S}/AVSCommon/SDKInterfaces/test/AVSCommon/SDKInterfaces/* ${D}${includedir}/AVSCommon/SDKInterfaces/test

    # Install Sample application to test Alexa Voice Service
    install -d ${D}${bindir}
    install -m 0755 ${B}/SampleApp/src/SampleApp ${D}${bindir}

    chrpath -d ${D}${bindir}/SampleApp
}

TARGET_CXXFLAGS+= "-Wno-deprecated-copy"
