.DEFAULT_GOAL := help

package: build ## Packager l'application dans un fichier .zip
	cd $(BUILD) \
		&& zip --quiet --recurse-paths $(APP_NAME).zip $(APP_DIR)

build: .check-app-name .prepare-build-dir ## Construire l'application
	cp --update --recursive src lib bin $(BUILD_APP)
	cd $(BUILD_APP) \
		&& mv src/$(STARTER_APP).java src/$(APP_NAME).java \
		&& mv bin/$(STARTER_APP).sh bin/$(APP_NAME).sh

install: .check-app-name .check-install-dir ## Installer le package de l'application
	unzip -q -d $(DEST_DIR) $(BUILD)/$(APP_NAME).zip

test: ## Tester l'application localement
	./bin/$(STARTER_APP).sh

test-from-java: ## Tester l'application localement (en lançant le fichier .java)
	./src/$(STARTER_APP).java

test-install: .check-app-name .check-install-dir ## Tester l'application installée
	PATH=$(DEST_DIR)/$(APP_DIR)/bin:$(PATH) $(APP_NAME).sh

clean: ## Nettoyer le répertoire de build
	rm --force --recursive $(BUILD)

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| sort \
		| awk 'BEGIN {FS = ":.*?## "}; { printf("\033[36m%-30s\033[0m %s\n", $$1, $$2) }'

.check-app-name:
ifndef APP_NAME
	$(error Please set the application name with the APP_NAME variable)
endif

.check-install-dir:
ifndef DEST_DIR
	$(error Please set the installation directory, for example DEST_DIR=~ make install)
endif

.prepare-build-dir:
	mkdir --parents $(BUILD_APP)/src $(BUILD_APP)/lib $(BUILD_APP)/bin

.PHONY: build

ifndef APP_DIR
APP_DIR := $(shell echo $(APP_NAME) | tr '[:upper:]' '[:lower:]')
endif

BUILD := build
STARTER_APP := Application
BUILD_APP := $(BUILD)/$(APP_DIR)
