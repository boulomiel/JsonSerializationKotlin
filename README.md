# JsonSerializationKotlin

Here we'll see an example os Kotlin.serialization coupled with Rx and ViewModel


# Setup 

    apply plugin: 'kotlinx-serialization'
    apply plugin: "org.jetbrains.kotlin.plugin.serialization"
    
    ...
    
    
      compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    
    ...
    
    dependencies{
    
    implementation "io.reactivex.rxjava3:rxkotlin:3.0.0"
    implementation "io.reactivex.rxjava3:rxandroid:3.0.0"

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")

    implementation 'androidx.recyclerview:recyclerview:1.1.0'

    }

# What is this about ?

In this example :

  1- we will first create an Observable<List<Repo>> from Github's repos api within the viewModel.
  
    var result = URL("url").readText()
    Json {ignoreUnknownKeys = true  }.decodeFromString(URL(result).readText())
    
  2- Update our MutableLiveData in the View Model when w susbcribing to the Observable<List<Repo>>
  
  3- Observe the MutableLiveData in the MainActivity to update our UI consequently (here a Recycler View)
