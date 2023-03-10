############################################################################
##
## Copyright (C) 2017 The Qt Company Ltd.
## Contact: https://www.qt.io/licensing/
##
## This file is part of the Boot to Qt meta layer.
##
## $QT_BEGIN_LICENSE:GPL$
## Commercial License Usage
## Licensees holding valid commercial Qt licenses may use this file in
## accordance with the commercial license agreement provided with the
## Software or, alternatively, in accordance with the terms contained in
## a written agreement between you and The Qt Company. For licensing terms
## and conditions see https://www.qt.io/terms-conditions. For further
## information use the contact form at https://www.qt.io/contact-us.
##
## GNU General Public License Usage
## Alternatively, this file may be used under the terms of the GNU
## General Public License version 3 or (at your option) any later version
## approved by the KDE Free Qt Foundation. The licenses are as published by
## the Free Software Foundation and appearing in the file LICENSE.GPL3
## included in the packaging of this file. Please review the following
## information to ensure the GNU General Public License requirements will
## be met: https://www.gnu.org/licenses/gpl-3.0.html.
##
## $QT_END_LICENSE$
##
############################################################################

DESCRIPTION = "QtGlesStream"
LICENSE = "The-Qt-Company-Commercial"
LIC_FILES_CHKSUM = "file://qtglesstream.pro;md5=875f34471470b09977705aed7863e440"

inherit qt5-module

SRC_URI = " \
    git://codereview.qt-project.org/qt/tqtc-qtglesstream;branch=${BRANCH};protocol=ssh \
    "

PV = "1.0.0"
SRCREV = "37f98d4b7123378d1ade7beedf5c5150cd26e178"
BRANCH = "dev"

S = "${WORKDIR}/git"

DEPENDS = "qtbase"

do_install:append() {
    install -m 0755 -d ${D}${includedir}/EGL
    install -m 0755 ${S}/headers/EGL/* ${D}${includedir}/EGL

    install -m 0755 -d ${D}${includedir}/GLES2
    install -m 0755 ${S}/headers/GLES2/* ${D}${includedir}/GLES2

    install -m 0755 -d ${D}${includedir}/GLES3
    install -m 0755 ${S}/headers/GLES3/* ${D}${includedir}/GLES3

    install -m 0755 -d ${D}${includedir}/KHR
    install -m 0755 ${S}/headers/KHR/* ${D}${includedir}/KHR
}

# no not overwrite files from qtglesstream-dummy-client
do_populate_sysroot[noexec] = "1"
