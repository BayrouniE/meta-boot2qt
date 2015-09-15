#############################################################################
##
## Copyright (C) 2014 Digia Plc and/or its subsidiary(-ies).
##
## This file is part of the Qt Enterprise Embedded Scripts of the Qt
## framework.
##
## $QT_BEGIN_LICENSE$
## Commercial License Usage Only
## Licensees holding valid commercial Qt license agreements with Digia
## with an appropriate addendum covering the Qt Enterprise Embedded Scripts,
## may use this file in accordance with the terms contained in said license
## agreement.
##
## For further information use the contact form at
## http://www.qt.io/contact-us.
##
##
## $QT_END_LICENSE$
##
#############################################################################

DESCRIPTION = "Virtual input plugin for QtSimulator"
LICENSE = "QtEnterprise"
LIC_FILES_CHKSUM = "file://qvinput.h;md5=ba04e32af7257890758a149b0c14d11a;beginline=1;endline=17"

inherit qt5-module

SRC_URI = " \
    git://codereview.qt-project.org/tqtc-boot2qt/emulator;branch=${BRANCH};protocol=ssh \
    "

SRCREV = "89ca944fae7106a55803ddce6fd84447685b61e5"
BRANCH = "master"

EXTRA_QMAKEVARS_PRE += "CONFIG+=force_independent"

S = "${WORKDIR}/git/src/helperlibs/vinput"

DEPENDS = "qtbase qtsimulator"
