if [ "$SCRIPTS_MODE" = true ] ; then
    find ${project.name}/scripts -type f -exec /${project.name}/bin/${project.name} cli '{}' ';'
else
    find ${project.name}/plugins -name "*.jar" -type f -exec cp '{}' ${project.name}/plugins ';'
    cd /
    /${project.name}/bin/${project.name} $@
fi