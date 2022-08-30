import Head from 'next/head'
import Image from 'next/image'
import Link from 'next/link'
import Landing from '../components/Landing'
import Header from '../components/Header'

export default function Home() {
  return (
    <div>
      <Header />
      <Landing />
    </div>
  )
}