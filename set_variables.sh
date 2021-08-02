#!/bin/bash
#
# Copyright 2021 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# The Google Cloud project to use for this tutorial bold-commerce-dlp
# you have to run
#             source ./set_variables.sh
export PROJECT=""

# The Compute Engine region to use for running Dataflow jobs and create a
# temporary storage bucket
export REGION=""

# define the GCS bucket to use as temporary bucket for Dataflow
#export TEMP_GCS_BUCKET="dlp_poc_kxusandbox-sada_com"
#export TEMP_GCS_BUCKET="bold-commerce-dlp"
export BUCKET=""

export FOLDER=""

export PACKAGE=""

export DATASET=""