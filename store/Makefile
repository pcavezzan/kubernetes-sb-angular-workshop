MODULE			= $(shell env $(GO) list -m)
PROJECTNAME 	= store
BIN 			= ./bin

ROOT_DIR:=$(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

.PHONY: help build

.DEFAULT_GOAL := help

help: Makefile
	@echo
	@echo "Project \033[31m$(PROJECTNAME)\033[0m"", please choose a command to run"
	@echo
	@grep -E '^[a-zA-Z\._-]+:.*?## .*$$' $(MAKEFILE_LIST) | \
		awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-25s\033[0m %s\n", $$1, $$2}'
	@echo

$(BIN):
	@mkdir -p $@

clean: ## Remove
	@rm -rf $(BIN)

build:	## Build the application locally
	go build ${LDFLAGS} -o $(BIN)/${PROJECTNAME}
