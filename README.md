# hello_yo
====================================================
	如何编译			
		第一次编译	bitbake hello_yo		
		重编译	bitbake -f -c build hello_yo	2019/10/09	
		clean	bitbake -c clean hello_yo		
		强制clean	bitbake -f -c clean hello_yo		

====================================================

手工安装yocto			
	第一步是源的问题		2019/09/30	"这里卡的没想到是最长的。
这里有几件事，

1. 所有的都去掉，只留下中科大的
https://blog.csdn.net/xiangxianghehe/article/details/80112149
国内有很多Ubuntu的镜像源，包括阿里的、网易的，还有很多教育网的源，比如：清华源、中科大源。
我们这里以中科大的源为例讲解如何修改Ubuntu 18.04里面默认的源。
编辑/etc/apt/sources.list文件, 在文件最前面添加以下条目(操作前请做好相应备份)：

##中科大源

deb https://mirrors.ustc.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-updates main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-backports main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-security main restricted universe multiverse
deb https://mirrors.ustc.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src https://mirrors.ustc.edu.cn/ubuntu/ bionic-proposed main restricted universe multiverse
-------------------------------------
2。 有一些单独装的软件，也会来捣乱，它们在哪呢？要把它们全干掉：
/etc/apt/sources.list.d

3. 然后
sudo apt-get update
sudo apt-get upgrade"
	第二步是安装yocto:注意，装的是支撑，yocto只能下代码，手工启动	https://linux.cn/article-8268-1.html?amputm_medium=rss	2019/09/30	"1. 
apt-get update
--------------------------------------
2. 
apt-get install wget git-core unzip make gcc g++ build-essential subversion sed autoconf automake texi2html texinfo coreutils diffstat python-pysqlite2 docbook-utils libsdl1.2-dev libxml-parser-perl libgl1-mesa-dev libglu1-mesa-dev xsltproc desktop-file-utils chrpath groff libtool xterm gawk fop
--------------------------------------
3. 
在这个教程中，系统上克隆的是 poky 的 morty 稳定分支。

 git clone -b morty git://git.yoctoproject.org/poky.git
"
	"第三步是解决python的问题，只能用python2,不能用python3"		2019/09/30	"yocto采用的是python 2.7
而我现在的机器装的是python3
这里很让人难以处理

网上有个牛人用最简单粗暴但有效的办法来解决了这个事：
mkdir ~/bin
PATH=~/bin:$PATH
ln -s /usr/bin/python2 ~/bin/python
To stop using python2, exit or rm ~/bin/python.

https://stackoverflow.com/questions/7237415/python-2-instead-of-python-3-as-the-temporary-default-python
我喜欢"
	设置起始的环境		2019/09/30	"source oe-init-build-env
不这，这条指令无法执行。
因为它需要知道 BBPATH
所以，只能在工程目录设置BBPATH之后，在工程目录，设用带绝对路径指向之（或相对路径指向之）
======================
另外，在PATH中，需要将两个路径加入：script和bin

===================yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ echo $PATH
/home/yujie-yocto/yo/poky/scripts:/home/yujie-yocto/yo/poky/bitbake/bin:/home/yujie-yocto/bin:/home/yujie-yocto/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin"
具体的操作实例			
	HelloWorld的网页			
		yocto下的bb版本hello world	https://blog.csdn.net/baidu_36516268/article/details/53130560	2019/10/09	"
yocto下的bb版本hello world
2016-11-11 17:16:45 孙浩凯 阅读数 2279更多
分类专栏： yocto
版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。
本文链接：https://blog.csdn.net/baidu_36516268/article/details/53130560
要运行yocto的hello world，按照步骤一步一步来






1搭建bitbake环境，如果有其他提示需要安装的，按照提示安装即可
sudo apt-get install bitbake


2配置BBPATH路径
mkdir yo
cd yo
export BBPATH=""$(pwd)""


3在yo路径下添加base.bbclass 和bitbake.conf文件
mkdir conf
cd conf
把下记内容放到bitbake.conf中
TMPDIR  = ""${TOPDIR}/tmp""
CACHE   = ""${TMPDIR}/cache""
STAMP   = ""${TMPDIR}/stamps""
T       = ""${TMPDIR}/work""
B       = ""${TMPDIR}""
BBFILES += ""${BBPATH}/recipes/*.bb""


cd ..
mkdir class
cd class
把下记内容放到base.bbclass中


BB_DEFAULT_TASK = ""build""    //设置默认的task为build，如果不设置的话其实默认也是Build


addtask build
addtask clean


4放一个bb文件用来打印hello_yocto，bb文件名称最好和bb配方名称一样，这里配方的名称起名为hello_yo.bb
DESCRIPTION = ""Prints Hello World""
PN = 'hello_yo'
PV = '1'


python do_build() {
    bb.plain(""********************"");
    bb.plain(""*                  *"");
    bb.plain(""*  Hello, World!   *"");
    bb.plain(""*                  *"");
    bb.plain(""********************"");
}
do_clean() {
    rm -rf ${BBPATH}/tmp
}
4这样我们就可以打出yoctobb版本的hello world了
执行 bitbake hello_yo就可以啦。






Hello World~"
	hello，我的实现			
		build	临时目录不需要关注		
		classes			
			base.bbclass		2019/10/09	"BB_DEFAULT_TASK = ""build""

addtask build
addtask clean
"
		conf			
			bitbake.conf		2019/10/09	"TMPDIR? = ""${TOPDIR}/tmp""
CACHE?? = ""${TMPDIR}/cache""
STAMP?? = ""${TMPDIR}/stamps""
T?????? = ""${TMPDIR}/work""
B?????? = ""${TMPDIR}""
BBFILES += ""${BBPATH}/recipes/*.bb""
"
		recipes			
			hello_yo.bb		2019/10/09	"DESCRIPTION = ""Prints Hello World""
PN='hello_yo'
PV='1'

python do_build() {
    bb.debug(2, ""Starting to figure out the task list"");
    bb.plain(""********************"");
    bb.plain(""Hello yo"");
    bb.plain(""********************"");
    bb.plain(""********************"");
    bb.note(""There are 47 tasks to run"");
}

python do_clean() {
    bb.plain("" clean"");
}

"
	编译前准备			
		先到工程所在目录	"export BBPATH=""$(pwd)"""		
		就在工程所在目录执行 /yocto的目录	. oe-init-build-env		
		然后就可以工作了			
	如何编译			
		第一次编译	bitbake hello_yo		
		重编译	bitbake -f -c build hello_yo	2019/10/09	
		clean	bitbake -c clean hello_yo		
		强制clean	bitbake -f -c clean hello_yo		
	执行的结果			"wrsadmin@pek-yhao-d1:~/yo$ bitbake -c clean hello_yo
Parsing recipes: 100% |###########################################################################################################################################| Time: 0:00:00
Parsing of 1 .bb files complete (0 cached, 1 parsed). 1 targets, 0 skipped, 0 masked, 0 errors.
NOTE: Resolving any missing task queue dependencies
Initialising tasks: 100% |########################################################################################################################################| Time: 0:00:00
NOTE: Executing RunQueue Tasks
NOTE: Tasks Summary: Attempted 1 tasks of which 1 didn't need to be rerun and all succeeded.
wrsadmin@pek-yhao-d1:~/yo$ bitbake hello_yo
Parsing recipes: 100% |###########################################################################################################################################| Time: 0:00:00
Parsing of 1 .bb files complete (0 cached, 1 parsed). 1 targets, 0 skipped, 0 masked, 0 errors.
NOTE: Resolving any missing task queue dependencies
Initialising tasks: 100% |########################################################################################################################################| Time: 0:00:00
NOTE: Executing RunQueue Tasks
NOTE: Tasks Summary: Attempted 1 tasks of which 1 didn't need to be rerun and all succeeded.
wrsadmin@pek-yhao-d1:~/yo$ bitbake -f -c clean hello_yo
Parsing recipes: 100% |###########################################################################################################################################| Time: 0:00:00
Parsing of 1 .bb files complete (0 cached, 1 parsed). 1 targets, 0 skipped, 0 masked, 0 errors.
NOTE: Resolving any missing task queue dependencies
Initialising tasks: 100% |########################################################################################################################################| Time: 0:00:00
NOTE: Executing RunQueue Tasks
None do_clean:  clean
NOTE: Tasks Summary: Attempted 1 tasks of which 0 didn't need to be rerun and all succeeded.
wrsadmin@pek-yhao-d1:~/yo$ bitbake -f -c build hello_yo
Parsing recipes: 100% |###########################################################################################################################################| Time: 0:00:00
Parsing of 1 .bb files complete (0 cached, 1 parsed). 1 targets, 0 skipped, 0 masked, 0 errors.
NOTE: Resolving any missing task queue dependencies
Initialising tasks: 100% |########################################################################################################################################| Time: 0:00:00
NOTE: Executing RunQueue Tasks
None do_build: ********************
None do_build: Hello yo
None do_build: ********************
None do_build: ********************
NOTE: Tasks Summary: Attempted 1 tasks of which 0 didn't need to be rerun and all succeeded.
"
	本机			
		2019-10-9 10:06:45		2019/10/09	"=============
1. 修改源
=============
 1783  sudo vim ./sources.list
 1784  sudo apt-get update
 
 第一轮安装，各种不灵。
 1785  sudo apt-get install bitbake # 出错了，装不上。
 1786  sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib build-essential chrpath socat libsdl1.2-dev
 1787  sudo apt-get update
 1788  bitbake
 
 1790  sudo apt-get install libsdl1.2-dev xterm sed cvs subversion coreutils texi2html docbook-utils python-pysqlite2 help2man make gcc g++ desktop-file-utils libgl1-mesa-dev libglu1-mesa-dev mercurial autoconf automake groff curl lzop asciidoc
 1791  ls
 
 
 有一些原，需要手工删除。
 1792  cd sources.list.d
 1796  mv webupd8team-ubuntu-java-bionic.list ../
 1797  sudo cp webupd8team-ubuntu-java-bionic.list ../
 
 删除conda
 1807  sudo apt remove conda
 1808  echo $PATH
 1809  cd ~
 1810  cat /etc/environment 
 干掉conda加入的path的代码
 1811  cat .profile 
 1812  cat /etc/profile
 1813  cd /etc/
 1814  cd profile.d/
 1815  ls
 1816  grep conda
 1817  grep conda *
 1818  cat ./bash_completion.sh 
 1819  which conda
 1820  cd /home/wrsadmin/miniconda3/bin/conda
 1821  cd /home/wrsadmin/miniconda3/
 1822  ls
 1823  cd ..
 1824  ls
 1825  pwd
 1826  grep -R conda *
 1827  rm -rf ./dist/
 1828  rm -rf ./dist/ &
 1829  ls
 1830  ls -a ./.*
 1831  vim .condarc
 1832  ls
 1833  grep -R conda *
 删除conda
 1834  rm -rf ~/miniconda &&  rm -rf ~/.condarc  ~/.conda ~/.continuum
 1835  sudo apt-get install wget git-core unzip make gcc g++ build-essential subversion sed autoconf automake texi2html texinfo coreutils diffstat python-pysqlite2 docbook-utils libsdl1.2-dev libxml-parser-perl libgl1-mesa-dev libglu1-mesa-dev xsltproc desktop-file-utils chrpath groff libtool xterm gawk fop
 1836  apt-get update
 1837  sudo apt-get update
 1838  pwd
 1839  cd yocto
 1840  mkdre yocto 
 1841  mkdir yocto
 1842  cd yocto
 1843  ls
 1844  cd poky/
 1845  ls
 尝试启动yocto，失败，因为现在是python3，需要改到python2
 1846  . oe-init-build-env
 1847  virtualenv -p /usr/bin/python2.7 --distribute temp-python
 1848  ls
 1849  source temp-python/bin/activate
 1850  mkdir ~/bin
 1851  PATH=~/bin:$PATH
 1852  ln -s /usr/bin/python2 ~/bin/python
 1853  ls
 1854  . oe-init-build-env
 1855  bitbake
 1856  cd ~
 1857  ls
 1858  cd pycharm-community-2019.2.2/
 1859  l
 1860  ls
 1861  cd bin
 1862  ls
 1863  ./pycharm.sh 
 1864  ssh yhao@pek-lpd-ccm1
 1865  sudo apt update
 1866  sudo apt-get install bitbake
 1867  sudo software-properties-gtk
 1868  echo $PATH
 1869  grep -rns ./.* /home/wrsadmin/miniconda3/bin
 1870  grep -rns ./.* ""/home/wrsadmin/miniconda3/bin""
 1871  grep -rns  ""/home/wrsadmin/miniconda3/bin"" ./.
 1872  cat /etc/bashrc
 1873  cat .bashrc
 1874  vim ./bashrc
 1875  vim ./.bashrc
 1876  echo $PATH
 1877  python
 1878  ls 
 1879  bitbake
 1880  cd yocto/
 1881  ls
 1882  cd poky/
 1883  ls
 1884  . oe-init-build-env
 1885  bitbake
 1886  cd ..
 1887  ls
 1888  cd ..
 1889  cd yo
 1890  ls
 1891  mkdir recipes
 1892  cd recipes/
 1893  vim ./hello_yo.bb
 1894  cd ..
 1895  ls
 1896  find ./ -name hell
 1897  find ./ -name hell*
 1898  rm ./class/hello_yo.bb 
 1899  find ./ -name hell
 1900  find ./ -name hell*
 1901  bitbake hello_yo
 1902  export BBPATH=""$(pwd)""
 1903  bitbake hello_yo
 1904  ls
 1905  cd class/
 1906  ls
 1907  vim ./base.bbclass 
 1908  cd ..
 1909  bitbake hello_yo
 1910  grep -rns classes ./.
 1911  mv ./class/ ./classes
 1912  bitbake hello_yo
 1913  vim ./classes/base.bbclass 
 1914  bitbake hello_yo
 1915  ls
 1916  cd recipes/
 1917  ls
 1918  vim ./hello_yo.bb 
 1919  cd ..
 1920  bitbake hello_yo
 1921  vim ./recipes/hello_yo.bb 
 1922  bitbake hello_yo
 1923  vim ./recipes/hello_yo.bb 
 1924  bitbake hello_yo
 1925  vim ./recipes/hello_yo.bb 
 1926  bitbake hello_yo
 1927  vim ./recipes/hello_yo.bb 
 1928  bitbake hello_yo
 1929  vim ./recipes/hello_yo.bb 
 1930  bitbake hello_yo
 1931  vim ./recipes/hello_yo.bb 
 1932  bitbake hello_yo
 1933  vim ./recipes/hello_yo.bb 
 1934  bitbake hello_yo
 1935  bitbake hello_yo -C clean
 1936  bitbake hello_yo
 1937  vim ./recipes/hello_yo.bb 
 1938  bitbake hello_yo
 1939  bitbake hello_yo -C cleanall
 1940  bitbake hello_yo 
 1941  vim ./recipes/hello_yo.bb 
 1942  bitbake hello_yo 
 1943  ls
 1944  rm -rf tmp
 1945  bitbake hello_yo 
 1946  vim ./recipes/hello_yo.bb 
 1947  bitbake hello_yo -C clean
 1948  ls
 1949  cd tmp
 1950  ls
 1951  cd ..
 1952  vim ./recipes/hello_yo.bb 
 1953  bitbake hello_yo -C clean
 1954  bitbake  hello_yo -C do_clean
 1955  ls
 1956  vim ./classes/base.bbclass 
 1957  bitbake  hello_yo clean
 1958  bitbake  hello_yo -C clean
 1959  bitbake  hello_yo -C clean -D
 1960  bitbake -f -c clean
 1961  bitbake -f -c clean hello_yo
 1962  vim ./recipes/hello_yo.bb 
 1963  bitbake -f -c clean hello_yo
 1964  vim ./recipes/hello_yo.bb 
 1965  bitbake -f -c clean hello_yo
 1966  vim ./recipes/hello_yo.bb 
 1967  bitbake -f -c clean hello_yo
 1968  vim ./recipes/hello_yo.bb 
 1969  bitbake -f -c clean hello_yo
 1970  ls
 1971  bitbake -f -c build hello_yo
 1972  bitbake -f -c clean hello_yo
 1973  ls
 1974  bitbake -f -c build hello_yo
 1975  ls
 1976  bitbake  -c clean hello_yo
 1977  ls
 1978  vim ./recipes/hello_yo.bb 
 1979  bitbake  -c clean hello_yo
 1980  vim ./recipes/hello_yo.bb 
 1981  bitbake  -c clean hello_yo
 1982  bitbake  hello_yo -c clean
 1983  ls
 1984  vim ./recipes/hello_yo.bb 
 1985  bitbake  hello_yo 
 1986  bitbake  hello_yo -c clean
 1987  vim ./recipes/hello_yo.bb 
 1988  bitbake  hello_yo -c clean
 1989  vim ./recipes/hello_yo.bb 
 1990  bitbake  hello_yo -c clean
 1991  vim ./recipes/hello_yo.bb 
 1992  pwd
 1993  which python
 1994  ls
 1995  cd pycharm-community-2019.2.2
 1996  cd bin
 1997  ls
 1998  ./pycharm.sh
 1999  ls
 2000  cd yo
 2001  ls
 2002  cd ..
 2003  cd yocto/
 2004  ls
 2005  cd poky/
 2006  ls
 2007  history
wrsadmin@pek-yhao-d1:~/yocto/poky$
"
		第二次使用遇到的问题			
			TEMPLATECONF value points to nonexistent directory			"wrsadmin@pek-yhao-d1:~/yocto/poky$ . oe-init-build-env
Error: TEMPLATECONF value points to nonexistent directory '
meta-poky/conf'
wrsadmin@pek-yhao-d1:~/yocto/poky$ 
"
			解决			也就是说，需要先设置BBPATH
				不能在yocto目录下执行 source oe-init-build-env	"1902  export BBPATH=""$(pwd)"""		"应当在要编译的根目录，就是BBPATH指定处
先到hello工程所在的位置
 1902  export BBPATH=""$(pwd)"""
	虚拟机			
		安装		2019/10/09	"yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ history
    1  sudo ls
    2  ls
    3  apt-get update
    4  sudo apt-get update
    5  ifconfig
    6  ping pek-yhao-d1
    7  hostname
    8  ifconfig
    9  python
   10  apt-get update
   11  sudo apt-get update
   12  apt-get install wget git-core unzip make gcc g++ build-essential subversion sed autoconf automake texi2html texinfo coreutils diffstat python-pysqlite2 docbook-utils libsdl1.2-dev libxml-parser-perl libgl1-mesa-dev libglu1-mesa-dev xsltproc desktop-file-utils chrpath groff libtool xterm gawk fop
   13  sudo apt-get install wget git-core unzip make gcc g++ build-essential subversion sed autoconf automake texi2html texinfo coreutils diffstat python-pysqlite2 docbook-utils libsdl1.2-dev libxml-parser-perl libgl1-mesa-dev libglu1-mesa-dev xsltproc desktop-file-utils chrpath groff libtool xterm gawk fop
   14  ls
   15  mkdir yo
   16  cd yo
   17  git clone -b morty git://git.yoctoproject.org/poky.git
   18  ls
   19  . ./oe-init-build-env
   20  pwd
   21  ls
   22  echo $PATH
   23  histroy
   24  history
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ 
"
		全部的log		2019/10/09	"yujie-yocto@yujieyocto-VirtualBox:~/yo/poky$ ls
bitbake        meta-poky      meta-yocto-bsp            README.hardware
documentation  meta-selftest  oe-init-build-env         scripts
LICENSE        meta-skeleton  oe-init-build-env-memres
meta           meta-yocto     README
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky$ . ./oe-init-build-env
You had no conf/local.conf file. This configuration file has therefore been
created for you with some default values. You may wish to edit it to, for
example, select a different MACHINE (target hardware). See conf/local.conf
for more information as common configuration options are commented.

You had no conf/bblayers.conf file. This configuration file has therefore been
created for you with some default values. To add additional metadata layers
into your configuration please add entries to conf/bblayers.conf.

The Yocto Project has extensive documentation about OE including a reference
manual which can be found at:
    http://yoctoproject.org/documentation

For more information about OpenEmbedded see their website:
    http://www.openembedded.org/


### Shell environment set up for builds. ###

You can now run 'bitbake <target>'

Common targets are:
    core-image-minimal
    core-image-sato
    meta-toolchain
    meta-ide-support

You can also run generated qemu images with a command like 'runqemu qemux86'
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ pwd
/home/yujie-yocto/yo/poky/build
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ ls
conf
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ echo $PATH
/home/yujie-yocto/yo/poky/scripts:/home/yujie-yocto/yo/poky/bitbake/bin:/home/yujie-yocto/bin:/home/yujie-yocto/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ histroy
histroy: command not found
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ history
    1  sudo ls
    2  ls
    3  apt-get update
    4  sudo apt-get update
    5  ifconfig
    6  ping pek-yhao-d1
    7  hostname
    8  ifconfig
    9  python
   10  apt-get update
   11  sudo apt-get update
   12  apt-get install wget git-core unzip make gcc g++ build-essential subversion sed autoconf automake texi2html texinfo coreutils diffstat python-pysqlite2 docbook-utils libsdl1.2-dev libxml-parser-perl libgl1-mesa-dev libglu1-mesa-dev xsltproc desktop-file-utils chrpath groff libtool xterm gawk fop
   13  sudo apt-get install wget git-core unzip make gcc g++ build-essential subversion sed autoconf automake texi2html texinfo coreutils diffstat python-pysqlite2 docbook-utils libsdl1.2-dev libxml-parser-perl libgl1-mesa-dev libglu1-mesa-dev xsltproc desktop-file-utils chrpath groff libtool xterm gawk fop
   14  ls
   15  mkdir yo
   16  cd yo
   17  git clone -b morty git://git.yoctoproject.org/poky.git
   18  ls
   19  . ./oe-init-build-env
   20  pwd
   21  ls
   22  echo $PATH
   23  histroy
   24  history
yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ 
"
		echo $PATH		2019/10/09	"yujie-yocto@yujieyocto-VirtualBox:~/yo/poky/build$ echo $PATH
/home/yujie-yocto/yo/poky/scripts:/home/yujie-yocto/yo/poky/bitbake/bin:/home/yujie-yocto/bin:/home/yujie-yocto/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin"

