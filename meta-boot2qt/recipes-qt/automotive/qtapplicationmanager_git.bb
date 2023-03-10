############################################################################
##
## Copyright (C) 2019 The Qt Company Ltd.
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

DESCRIPTION = "Qt component for application lifecycle management"
LICENSE = "(GFDL-1.3 & The-Qt-Company-GPL-Exception-1.0 & (LGPL-3.0 | GPL-2.0+)) | The-Qt-Company-Commercial"
LIC_FILES_CHKSUM = "file://LICENSE.GPL3;md5=d32239bcb673463ab874e80d47fae504"

inherit qt5-module
require recipes-qt/qt5/qt5-git.inc

SRCREV = "d4277ed9597a2277f6a32f3f8480d746cd020a82"

DEPENDS = "qtbase qtdeclarative libyaml libarchive \
           ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "qtwayland qtwayland-native", "", d)}"

RDEPENDS:${PN}_class-target = "libcrypto ${PN}-tools"

EXTRA_QMAKEVARS_PRE += "\
    ${@bb.utils.contains("DISTRO_FEATURES", "wayland", "-config force-multi-process", "-config force-single-process", d)} \
    -config install-prefix=/usr \
    -config systemd-workaround \
    -config hardware-id=neptune \
    "

do_install:append() {
    install -m 0755 -d ${D}/opt/am/
    install -m 0644 ${S}/template-opt/am/config.yaml ${D}/opt/am/
}

FILES_${PN} += "\
    /opt/am \
    "

BBCLASSEXTEND += "nativesdk native"

# nativesdk-qtdeclarative is added only to make build deterministic, can be removed once
# there is a configure option to disable its usage.
DEPENDS:class-nativesdk = "qtbase nativesdk-qtdeclarative nativesdk-glibc-locale nativesdk-libarchive"
DEPENDS:class-nativesdk:remove:mingw32 = "nativesdk-glibc-locale nativesdk-libarchive"

EXTRA_QMAKEVARS_PRE_class-nativesdk = "\
    -config tools-only \
    "
EXTRA_QMAKEVARS_PRE_class-native = "\
    -config tools-only \
    "
