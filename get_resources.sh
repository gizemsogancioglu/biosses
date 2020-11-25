#!/bin/bash
#
# Copyright (C) 2017  Fitsum <fgaim@geezlab.com>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.


# Download resources from bitbucket repo
DIR="./src/main/resources"
mkdir -p $DIR
wget -nc https://bitbucket.org/gizemsogancioglu/biosses-resources/raw/52a77008d6c80ea570fa717136421b8c81683aa2/resources.zip -O "$DIR/resources.zip"

# Uncompress
cd $DIR
unzip -u resources.zip
