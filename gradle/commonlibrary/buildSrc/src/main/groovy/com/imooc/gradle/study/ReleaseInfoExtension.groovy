/**
 * 与自定义PLugin进行参数传递
 */
class ReleaseInfoExtension {

    String versionCode
    String versionName
    String versionInfo
    String fileName

    ReleaseInfoExtension() {

    }

    @Override
    String toString() {
        """| versionCode = ${versionCode}
           | versionName = ${versionName}
           | versionInfo = ${versionInfo}
           | fileName = ${fileName}
        """.stripMargin()
    }
}