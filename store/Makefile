MODULE			= $(shell env $(GO) list -m)
PROJECTNAME 	= store
BIN 			= ./bin
COVERAGE		= ./coverage

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

clean:  ## Remove
	go clean -i ./...
	rm -rf $(BIN) $(COVERAGE)

build:	## Build the application locally
	go build ${LDFLAGS} -o $(BIN)/${PROJECTNAME}

test:	## Run all tests
	go test ./... -v

coverage:		## Run test coverage and generate html report
	@mkdir -p coverage
	@go list -f '{{if gt (len .TestGoFiles) 0}}"go test -covermode count -coverprofile {{.Name}}.coverprofile -coverpkg ./... {{.ImportPath}}"{{end}}' ./... | xargs -I {} bash -c {}
	@echo "mode: count" > $(COVERAGE)/cover.out
	@grep -h -v "^mode:" *.coverprofile >> "$(COVERAGE)/cover.out"
	@rm *.coverprofile
	@go tool cover -html=$(COVERAGE)/cover.out -o=$(COVERAGE)/cover.html

