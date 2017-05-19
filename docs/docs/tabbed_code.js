var tabIdx = 1;

// If back-to-back code snippets are found, convert them into a tabbed view instead
eles = document.getElementsByClassName("col-md-9")[0].children
for (var i=0; i < eles.length; i++) {
        if (isCodeExample(eles[i])) {
        // Collect all adjacent code examples to the above one
        matches=[];
        for (j=i; j < eles.length && isCodeExample(eles[j]); j++) {
            matches[j-i]=eles[j]
        }
        if(matches.length > 1) {
            console.log("Found " + matches.length + " adjacent code samples, combining")
            makeTabs(matches)
            prepareExamples(matches)
            tabIdx += 1
            i += matches.length + 1
        } else {
            console.log("Standalone code sample detected, not doing anything")
        }
    }
}
function isCodeExample(ele) {
    // TODO: more reliable identification
    return ele.localName == "pre" && ele.firstChild.localName == "code"
}

function harvestLangName(ele) {
        name = ele.children[0].className.replace("hljs", "")
        name = name[0].toUpperCase() + name.slice(1) 
        name = name.replace(/\W/g, '')
        return name
}

function switchToTab(tabUUID, lang, button) {
    buttons = button.parentElement.children
    console.log(button.parentElement.children)
    for (b of buttons) {
        console.log("Trying "+b)
        if (b.innerText == lang) {
            b.style.backgroundColor = "#f1f1f1"
        } else {
            b.style.backgroundColor = "#c1c1c1"
        }
    }
    tabs = document.getElementsByClassName(tabUUID)
    for (tab of tabs) {
        if (tab.classList.contains(lang)) {
            tab.style.display = "block"
        } else {
            tab.style.display = "none"
        }
    }
    console.log("Switching to " + lang + " for tab " + tabUUID)
}
function getTabUUID() {
    return tabIdx + "__tab"
}

function makeTabs(eles) {
    tabHTML = '<div class="tab">'
    for(var i=0; i< eles.length; i++) {
        ele = eles[i]
        name = harvestLangName(ele)
        // TODO: this should all be CSS somewhere
        buttonStyle = 'padding: 6px 12px; border: none; background-color: '
        if(i==0) { 
            buttonStyle += "#f1f1f1;"
        } else {
            buttonStyle += "#c1c1c1;"
        }
        tabHTML += '<button class="tablinks" style="' + buttonStyle + '" onclick="switchToTab(\'' + getTabUUID() + '\',\''+name+'\', this)">' + name + '</button>'
    }
    tabHTML += '</div>'

    newDiv = document.createElement("div")
    newDiv.innerHTML = tabHTML

    eles[0].parentElement.insertBefore(newDiv, eles[0])
}

function prepareExamples(eles) {
    for (var i=0; i < eles.length; i++) {
        name = harvestLangName(eles[i])
        eles[i].classList.add(getTabUUID())
        eles[i].classList.add(name)
        // Hide all but the first (default) tab
        if(i > 0) {
            eles[i].style.display = "none"
        }
    }
}

