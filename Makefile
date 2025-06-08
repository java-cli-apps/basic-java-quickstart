help: ## Afficher l'aide
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) \
		| awk 'BEGIN {FS = ":.*?## "}; { printf("\033[36m%-20s\033[0m %s\n", $$1, $$2) }'

package: build ## Packager l'application dans un fichier .zip
	cd $(BUILD) \
		&& zip --quiet --recurse-paths $(APP_NAME).zip $(APP_DIR)

install: .check-install-dir ## Installer le package de l'application
	unzip -q -d $(DEST_DIR) $(BUILD)/$(APP_NAME).zip

build: ## Construire l'application
	mkdir --parents $(BUILD_APP)
	cp --update --recursive src lib bin $(BUILD_APP)
	cd $(BUILD_APP) \
		&& mv src/$(STARTER_APP).java src/$(APP_NAME).java \
		&& mv bin/$(STARTER_APP).sh bin/$(APP_NAME).sh

clean: ## Nettoyer le répertoire de construction
	rm --force --recursive $(BUILD)

test: ## Tester l'application localement
	./bin/$(STARTER_APP).sh

test-from-java: ## Tester l'application localement (en lançant le fichier .java)
	./src/$(STARTER_APP).java

test-install: .check-install-dir ## Tester l'application installée
	PATH=$(DEST_DIR)/$(APP_DIR)/bin:$(PATH) $(APP_NAME).sh

.check-install-dir:
ifndef DEST_DIR
	$(error Please set the installation directory, for example DEST_DIR=~ make install)
endif

.PHONY: build

APP_NAME ?= BasicQuickstart
APP_DIR := $(APP_NAME)
BUILD := build
BUILD_APP := $(BUILD)/$(APP_DIR)
STARTER_APP := Application
