# KDB
Kotlin Databind library was made as an alternative to the Android Databind Library, this was constructed to be used with LiveData, so no changes to your ViewModel/Presenter is required to use this.

This library was made using extension function, since there is no code generation, no more hunting through a log to find a error in a 1000 errors log.

There are 4 libaries that you can add to your project

## Migrating from 1.0.0 to 2.0.0
:warning:

Due to **Jcenter shutdown**, the KDB project's maven group id was previously ```com.psato.kdbcore``` and is now ```dev.kdblib```. Please check your configuration as shown below.


## One Way Binding
To add to your project:
``` groovy
implementation 'dev.kdblib:onewaybind:2.0.0'
```
To use just add the interface **Bindable** to your class
``` kotlin
class QueryFragment : BaseFragment(), Bindable {
```
and just call the **bind** method
``` kotlin
queryViewModelArc.showLoading.bind { loadinLayout.present = it }
```

## Two Way Binding
To add to your project:
``` groovy
implementation 'dev.kdblib:twowaybind:2.0.0'
```
To use just add the interface **Bindable** to your class
``` kotlin
class QueryFragment : BaseFragment(), TwoWayBindable {
```
and just call the **bind** method
``` kotlin
queryViewModelArc.queryValue.twoWayBind(queryEditText.bindableText)
```
and to listem to update on the LiveData on your ViewModel implement the interface **LiveDataUpdateListener**

``` kotlin
class QueryViewModelArc
constructor(private val searchShows: SearchShows) : 
    ViewModel(), LiveDataUpdateListener {
```
and to listem to updates call the **addUpdateListener** method
``` kotlin
addUpdateListener(queryValue) { query ->
            searchEnabled(!TextUtils.isEmpty(query))
        }
```
This listener will me automatically removed when the ViewModel finishes

## Extensions
To add to your project:
``` groovy
implementation 'dev.kdblib:extensions:2.0.0'
```
This library contains several functions to make your life easier using ViewModel and Kdb

**LiveDataFactory** interface
``` kotlin
val queryValue by liveData("")
```
This method that creates a MutableLiveData and return it as a LiveData

**LiveDataSetter** interface
``` kotlin
showLoading(true)
```
This method require that this LiveData is a MutableLiveData,
cast it to MutableLiveData and set it's value

**ParentViewModel** interface
``` kotlin
val showItem = childViewModel { ShowResponseItem(response) }
```
Lazy ChildViewModel creation for the current  ParentViewModel. The child will be cleared when the parent finishes


**ChildViewModel** class

Used to when the itens inside a RecyclerView are ViewModels

**LifeCycleViewHolder** class

Makes the ViewHolder a LifeCyclerOwner to be used with LiveDatas inside ViewModels

## KDB
To add to your project:
``` groovy
implementation 'dev.kdblib:kdb:2.0.0'
```
Contains all the libraries from KDB package

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
