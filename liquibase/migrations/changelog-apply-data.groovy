databaseChangeLog() {
    changeSet(id: 'data-load-001', author: 'dmitriy'){
        sqlFile(path: '/migrations/data/data.sql')
    }
}