package mdev.mobile.domain.repo

import androidx.paging.DataSource

typealias IdType = Long
typealias DataSourceT<Key, Value> = DataSource<Key, Value>
typealias DataSourceFactoryT<Key, Value> = DataSource.Factory<Key, Value>
