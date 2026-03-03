# Locker
Helps you store your data in forms of 'json' and 'yaml'.
Provides a cachable api to store all the data and io operate all at one in order to reduce io cost

You can simply use this by calling com.fomdev.lock.api.JsonFileCache (for json) and com.fomdev.lock.api.YamlFileCache (for yaml)
You can simply passin the file namespace, 'no adding the suffix', the whole path of the file (will automatically create it for you) or directly pass in the target file you want to invoke.

By using the built-in method, you can use *.get() (all classes implemented this) to reformat the json or yaml file into a map of type Map<String, Object>
