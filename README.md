# KDB
Kotlin Databind library was made as an alternative to the Android Databind Library, this was constructed to be used with LiveData, so no changes to your ViewModel/Presenter is required to use this.

This library was made using extension function, since there is no code generation, no more hunting through a log to find a error in a 1000 erros log.

## Adding to project

To use it just add it to your app gradle file

```
implementation 'com.psato.kdbcore:kdb:0.3.0'
```

## Simple usage
Inside a LifeCycleOwner to perform a one way bind just call the method **bind**
```
bind(queryViewModelArc.searchEnabled, searchButton::setEnabled)
bind(queryViewModelArc.showLoading){ loadinLayout.present = it} 
```

To perform a two way bind call the method **twoWayBind**
```
twoWayBind(queryViewModelArc.queryValue, queryEditText.bindableText)
```


## License

Copyright 2019 Paulo Vitor Sato

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
